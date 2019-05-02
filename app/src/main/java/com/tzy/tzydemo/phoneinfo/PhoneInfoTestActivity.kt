package com.tzy.tzydemo.phoneinfo

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.wifi.WifiManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.tzy.tzydemo.R
import kotlinx.android.synthetic.main.activity_phone_info_test.*
import java.io.*
import java.net.NetworkInterface
import java.util.*

class PhoneInfoTestActivity : AppCompatActivity() {

    val dataList = arrayListOf<String>()
    var marshmallowMacAddress = "02:00:00:00:00:00"
    val fileAddressMac = "/sys/class/net/wlan0/address"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_info_test)


        dataList.add("fingerPrint:${Build.FINGERPRINT}")
        dataList.add("ID:${Build.ID}")
        dataList.add("brand:${Build.BRAND}")
        dataList.add("Device:${Build.DEVICE}")
        dataList.add("Model:${Build.MODEL}")
        dataList.add("SERIAL:${Build.SERIAL}")
        dataList.add("CPU:${Build.CPU_ABI}")
        dataList.add("lieyou get MacAddress:${getAdresseMAC(this)}")
        dataList.add("check VM by LightSensor:${notHasLightSensorManager()}")
        dataList.add("Android ID:${getAndroidId()}")
        dataList.add("IMEI:${getIMEI()}")
        dataList.add("IMSI:${getIMSI()}")


        rcy_phone_info_list.layoutManager = LinearLayoutManager(this)
        rcy_phone_info_list.adapter = DataAdapter()

    }

    /**
     *
     * 需要READ_PHONE_STATE权限
     * */
    fun getIMEI(): String {
        try {
            //实例化TelephonyManager对象
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            //获取IMEI号
            var imei = telephonyManager.getDeviceId()
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = ""
            }
            return imei
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }
    }

    fun getAndroidId(): String {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    fun getIMSI(): String {
        try {
            //实例化TelephonyManager对象
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            //获取IMEI号
            var imsi = telephonyManager.subscriberId
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imsi == null) {
                imsi = ""
            }
            return imsi
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }
    }


    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     * @return true 为模拟器
     */
    fun notHasLightSensorManager(): Boolean {
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) //光
        return null == sensor8
    }


    fun getAdresseMAC(context: Context): String? {
        val wifiMan = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInf = wifiMan.connectionInfo

        if (wifiInf != null && marshmallowMacAddress == wifiInf.macAddress) {
            var result: String? = null
            try {
                result = getAdressMacByInterface()
                if (result != null) {
                    return result
                }
                else {
                    result = getAddressMacByFile(wifiMan)
                    return result
                }
            } catch (e: Exception) {
                Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ")
            }

        }
        else {
            return if (wifiInf != null && wifiInf.macAddress != null) {
                wifiInf.macAddress
            }
            else {
                ""
            }
        }
        return marshmallowMacAddress
    }

    private fun getAdressMacByInterface(): String? {
        try {
            val all = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (nif.name.equals("wlan0", ignoreCase = true)) {
                    val macBytes = nif.hardwareAddress ?: return ""

                    val res1 = StringBuilder()
                    for (b in macBytes) {
                        res1.append(String.format("%02X:", b))
                    }

                    if (res1.length > 0) {
                        res1.deleteCharAt(res1.length - 1)
                    }
                    return res1.toString()
                }
            }

        } catch (e: Exception) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ")
        }

        return null
    }

    @Throws(Exception::class)
    private fun getAddressMacByFile(wifiMan: WifiManager): String {
        val ret: String
        val wifiState = wifiMan.wifiState

        wifiMan.isWifiEnabled = true
        val fl = File(fileAddressMac)
        val fin = FileInputStream(fl)
        ret = crunchifyGetStringFromStream(fin)
        fin.close()

        val enabled = WifiManager.WIFI_STATE_ENABLED == wifiState
        wifiMan.isWifiEnabled = enabled
        return ret
    }

    @Throws(IOException::class)
    private fun crunchifyGetStringFromStream(crunchifyStream: InputStream?): String {
        if (crunchifyStream != null) {
            val crunchifyWriter = StringWriter()

            val crunchifyBuffer = CharArray(2048)
            try {
                val crunchifyReader = BufferedReader(InputStreamReader(crunchifyStream, "UTF-8"))
                var counter: Int
                counter = crunchifyReader.read(crunchifyBuffer)
                while (counter != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter)
                    counter = crunchifyReader.read(crunchifyBuffer)
                }
            } finally {
                crunchifyStream.close()
            }
            return crunchifyWriter.toString()
        }
        else {
            return "No Contents"
        }
    }

    /**
     * 判断蓝牙是否有效来判断是否为模拟器
     *
     * @return true 为模拟器
     */
    fun notHasBlueTooth(): Boolean {
        val ba = BluetoothAdapter.getDefaultAdapter()
        if (ba == null) {
            return true
        }
        else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            val name = ba.name
            Toast.makeText(this, "blue tooth name == ${name}", Toast.LENGTH_SHORT).show()
            return TextUtils.isEmpty(name)
        }
    }

    inner class DataAdapter : RecyclerView.Adapter<PhoneInfoItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneInfoItemViewHolder {
            return PhoneInfoItemViewHolder(layoutInflater.inflate(R.layout.item_phone_info_item, parent, false))
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: PhoneInfoItemViewHolder, position: Int) {
            val data = dataList[position]
            holder.content?.text = data
        }

    }

    inner class PhoneInfoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var content: TextView? = null

        init {
            content = itemView.findViewById(R.id.tv_content)
        }


    }

}
