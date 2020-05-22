package mx.edu.ittepic.ladm_u4_tarea2_renteriareyes

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_llamadas.*

class LlamadasActivity : AppCompatActivity() {

    var llamadas =
        listOf<String>(CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE).toTypedArray()

    /*------------- REQUEST CODES----------*/
    var siPermisoLlamadas = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llamadas)
        /*--------------- VERIFICAR PERMISOS------------------------------*/
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { android.Manifest.permission.READ_CALL_LOG },
                siPermisoLlamadas
            )
        } else {
            registroLlamadas()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == siPermisoLlamadas && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registroLlamadas()
        }
    }

    private fun registroLlamadas() {
        var tipo = "3"
        var datos = listOf<String>(CallLog.Calls.NUMBER, CallLog.Calls.TYPE).toTypedArray()
        var datosEnPantalla = intArrayOf(R.id.textView2, R.id.textView3)
        /*------------------ HACER CONSULTAS--------------------------*/
        var cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI!!,
            llamadas,
            CallLog.Calls.TYPE + " = ?",
            arrayOf<String>(tipo.toString()),
            "${CallLog.Calls.LAST_MODIFIED}"
        )
        /*---------------------- MOSTRAR LLAMADAS PERDIDAS-----------------------------*/
        var adapter = SimpleCursorAdapter(
            applicationContext,
            R.layout.row_list,
            cursor,
            datos,
            datosEnPantalla,
            0
        )
        lista.adapter = adapter
    }
}
