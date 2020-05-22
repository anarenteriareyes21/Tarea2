package mx.edu.ittepic.ladm_u4_tarea2_renteriareyes

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.database.getLongOrNull
import kotlinx.android.synthetic.main.activity_calendario.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class CalendarioActivity : AppCompatActivity() {
    /*-------LISTA PARA GUARDAR LOS EVENTOS---*/
    var listaEventos = ArrayList<String>()

    /*----------- REQUEST CODES------------*/
    var siPermisoCalendario = 101
    /*------- OBETENR INFORMACION DEL EVENTO----*/
    var eventos = listOf<String>(CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        //Verificar si tiene el permiso
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED){
           //Si no tiene el permiso darselo:
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALENDAR), siPermisoCalendario)
        }else{
            verEventos()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == siPermisoCalendario && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            verEventos()
        }
    }
    /*----------------- VER LOS EVENTOS DEL CALENDARIO-------------*/
    private fun verEventos() {

        /*--------------- ESPECIFICAR UN CALENDARIO---------------------------*/
        val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"

        val selectionArgs: Array<String> = arrayOf("anarenteriareyes21@gmail.com", "com.google", "anarenteriareyes21@gmail.com")
        /*------------------------------------------------------------------------------------------------*/
        var cursor = contentResolver.query(
            CalendarContract.Events.CONTENT_URI!!,eventos,selection,selectionArgs,null
        )
        var resultado = ""
        if(cursor!!.moveToFirst()){
            var posicionTitulo = cursor.getColumnIndex(CalendarContract.Events.TITLE)
            var posicionLocacion = cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)
            var posicionHora = cursor.getColumnIndex(CalendarContract.Events.DTSTART)
            var posicionDescripcion = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION)
            var p = cursor.getColumnIndex(CalendarContract.Events.DTSTART)

            do {

                val dt = getShortDate(cursor.getLong(p))
                resultado += "TITULO: " + cursor.getString(posicionTitulo) +
                            "\nUBICACION: " + cursor.getString(posicionLocacion) +
                            "\nINICIO: " + cursor.getString(posicionHora) +
                            "\nDESCRIPCION: " +  cursor.getString(posicionDescripcion) +
                        "\nDATE: " + dt.toString() +

                            "\n ================================== \n\n"
                listaEventos.add(resultado)
            }while (cursor.moveToNext())
        }
        textView6.setText(resultado)

    }
    fun getShortDate(ts:Long?):String{
        if(ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts
        //return formatted date
        return android.text.format.DateFormat.format("E, dd MMM yyyy", calendar).toString()
    }

}
