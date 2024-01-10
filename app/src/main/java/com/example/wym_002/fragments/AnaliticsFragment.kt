package com.example.wym_002.fragments

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wym_002.databinding.DialogInfo2Binding
import com.example.wym_002.databinding.FragmentAnaliticsFragmentBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.WriteAbortedException

class AnaliticsFragment : Fragment() {

    private lateinit var binding: FragmentAnaliticsFragmentBinding

    private lateinit var dialog: Dialog
    private lateinit var dialogMyInfo: DialogInfo2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAnaliticsFragmentBinding.inflate(layoutInflater)

        binding.button.setOnClickListener{

            val string = binding.textView.text.toString()

            if (string.isNotEmpty()){
                generateQrCode(string)
            }
        }

        return binding.root
    }

    private fun generateQrCode(text: String) {
        try {
            val barcodeEncode = BarcodeEncoder()
            val bitmap = barcodeEncode.encodeBitmap(text, BarcodeFormat.QR_CODE, 750, 750)
            showDialogMyInfo(bitmap)
        } catch (_: WriteAbortedException) {

        }
    }

    private fun showDialogMyInfo(bitmap: Bitmap) {

        dialogMyInfo = DialogInfo2Binding.inflate(layoutInflater)
        dialog = Dialog(this.activity!!)
        dialogMyInfo.imageView.setImageBitmap(bitmap)
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

}