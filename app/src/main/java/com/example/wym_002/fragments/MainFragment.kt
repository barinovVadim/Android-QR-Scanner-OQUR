package com.example.wym_002.fragments


import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.wym_002.R
import com.example.wym_002.databinding.*
import com.example.wym_002.hidingPanel
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import kotlin.concurrent.thread


class MainFragment : Fragment(), ZBarScannerView.ResultHandler {

    private lateinit var dialog: Dialog
    private lateinit var dialogMyInfo: DialogInfoBinding

    private lateinit var zbView: ZBarScannerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        zbView = ZBarScannerView(this.activity!!)

        checkCameraPermission()

        return zbView
    }

    override fun onResume() {
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        onPause()
        showDialogMyInfo(result)

    }

    private fun showDialogMyInfo(string: Result?) {

        dialogMyInfo = DialogInfoBinding.inflate(layoutInflater)
        dialog = Dialog(this.activity!!)
        dialogMyInfo.textMyInfo1.text = string?.contents
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogMyInfo.root)
        dialog.setCancelable(true)

        dialogMyInfo.dialogBtn.setOnClickListener {
            dialog.dismiss()
            Thread.sleep(500)
            onResume()
        }

        dialog.show()

    }

    private fun checkCameraPermission(){
        if(ContextCompat.checkSelfPermission(this.activity!!, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this.activity!!,
                arrayOf(Manifest.permission.CAMERA), 12)

        } else {
            onResume()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 12){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                onResume()
            }
        }
    }

}