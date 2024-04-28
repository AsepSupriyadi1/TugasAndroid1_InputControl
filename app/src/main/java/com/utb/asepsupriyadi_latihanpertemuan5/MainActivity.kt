package com.utb.asepsupriyadi_latihanpertemuan5

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var txtDatePicker : EditText
    private lateinit var txtPhoneNumber : EditText
    private lateinit var txtNamaKontak : EditText

    private lateinit var txtDatePickerBtn : Button
    private lateinit var submitBtn : Button

    private lateinit var listKontak : TextView

    private val result : MutableList<String> = mutableListOf()

    private var resultStr : String = ""

    private var resultIndex : Int = 1


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtDatePicker = findViewById(R.id.date_txt)
        txtPhoneNumber = findViewById(R.id.phone_number_txt)
        txtNamaKontak = findViewById(R.id.nama_txt)

        txtDatePickerBtn = findViewById(R.id.choose_date_button)
        submitBtn = findViewById(R.id.submit_button)

        listKontak = findViewById(R.id.list_kontak_txt)

        var currentDate : LocalDate = LocalDate.now()
        var currentDateStr = "${currentDate.dayOfMonth} ${currentDate.month} ${currentDate.year}"

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Penambahan Data")
        builder.setMessage("Yakin ingin menambah kontak ini ?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            addNewData(
                txtNamaKontak.text.toString(),
                txtPhoneNumber.text.toString(),
                txtDatePicker.text.toString(),
                currentDateStr
            )
            displayData()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                "Mengedit kembali", Toast.LENGTH_SHORT).show()
        }




        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateSelectedDate(myCalendar)
        }

        txtDatePickerBtn.setOnClickListener{
            DatePickerDialog(
                this, datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        txtDatePicker.setText(currentDateStr)


        submitBtn.setOnClickListener {
            var kontakErr = txtNamaKontak.text.toString().equals("")
            var nomorErr = txtPhoneNumber.text.toString().equals("")

            if(kontakErr || nomorErr){
                Toast.makeText(applicationContext,
                    "Nama & Kontak wajib diisi !", Toast.LENGTH_SHORT).show()
            } else {
                builder.show()
            }
        }



    }


    private fun updateSelectedDate(myCalendar : Calendar){
        val dateFormat = "dd MMMM yyyy"
        val dateResult = SimpleDateFormat(dateFormat, Locale.UK)
        txtDatePicker.setText(dateResult.format(myCalendar.time))
    }

    private fun addNewData(
        namaKontakParam: String,
        nomorPonselParam: String,
        tanggalParam: String,
        tanggalSaatIni : String
    ){
        result.add(
            "${resultIndex}. " +
                    "${namaKontakParam}, " +
                    "${nomorPonselParam}" +
                    " (${tanggalParam})" +
                    "\n"
        )
        resultIndex++;
        Toast.makeText(applicationContext,
            "Data Berhasil Ditambahkan !", Toast.LENGTH_SHORT).show()
        txtNamaKontak.setText("")
        txtPhoneNumber.setText("")
        txtDatePicker.setText(tanggalSaatIni)
    }

    private fun displayData(){
        resultStr = "";
        for(data in result){
            resultStr += data
        }
        listKontak.text = resultStr;
    }

}