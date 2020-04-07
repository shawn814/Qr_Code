package com.example.reader

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() ,ResultHandler{
    private val REQUES_CAMERA = 1
    private var txtResult: TextView?= null
    private var scannerView:ZXingScannerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scannerView = findViewById(R.id.scanner)
        txtResult = findViewById(R.id.txtResult)
        if(!checkPermission())
            requestPermission()
    }
    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),REQUES_CAMERA)
    }

    override fun onResume() {
        super.onResume()
        if(checkPermission()){
            if(scannerView == null){
                scannerView = findViewById(R.id.scanner)
                setContentView(scannerView)
            }
            scannerView?.setResultHandler(this)
            scannerView?.startCamera()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()
    }

    override fun handleResult(p0: Result?) {
        val result:String? = p0?.text
        val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(1000)
        txtResult?.text = result
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
//        val builder:AlertDialog.Builder = AlertDialog.Builder(this)
//        builder.setTitle("Result")
//        builder.setPositiveButton("ok"){ dialog, which ->
//            scannerView?.resumeCameraPreview(this)
//            startActivity(intent)
//        }
//        builder.setMessage(result)
//        val alert:AlertDialog = builder.create()
//        alert.show()
    }

}
