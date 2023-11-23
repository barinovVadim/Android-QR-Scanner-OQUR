package com.example.wym_002.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.wym_002.R
import com.example.wym_002.databinding.CustomDialogLayoutBinding
import com.example.wym_002.databinding.DialogScrollDateBinding
import com.example.wym_002.databinding.FragmentSettingsFragmentBinding
import com.example.wym_002.hidingPanel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsFragmentBinding
    private lateinit var dialog: Dialog
    private lateinit var dialogScrollDate: DialogScrollDateBinding
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsFragmentBinding.inflate(layoutInflater)

        pref = context!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        // resultCardBalance        key: R.drawable.credit_card_white.toString()
        // resultWalletBalance      key: R.drawable.wallet_white.toString()
        // resultBankBalance        key: R.drawable.account_balance_white.toString()
        // resultTotalBalance       key: getString(R.string.keyTotalBalance)
        // checkSplashScreen        key: getString(R.string.flagSplashScreen)
        // setDateDay               key: getString(R.string.setDateDay)


        binding.switchSplashScreen.isChecked = pref.getInt(getString(R.string.flagSplashScreen), 1) == 1
        binding.textSetDate.text = pref.getInt(getString(R.string.setDateDay), 1).toString()

        buttonToInfo()

        buttonToClear()

        switchChecked()

        buttonToDate()

        return binding.root
    }

    private fun buttonToInfo() {

        val buttonClick1 = AlphaAnimation(1f, 0.7f)
        buttonClick1.duration = 160
        buttonClick1.fillAfter = false
        val buttonClick2 = AlphaAnimation(0.7f, 1f)
        buttonClick2.duration = 50
        buttonClick2.fillAfter = true
        buttonClick2.startOffset = 70

        binding.textInfo.setOnClickListener {

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            // TODO(нужно сделать переброс на диалоговое окно)

        }

        binding.imageInfo.setOnClickListener{

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            // TODO(нужно сделать переброс на диалоговое окно)

        }

    }

    private fun buttonToDate() {

        // параметры анимации нажатия
        val buttonClick1 = AlphaAnimation(1f, 0.7f)
        buttonClick1.duration = 160
        buttonClick1.fillAfter = false
        val buttonClick2 = AlphaAnimation(0.7f, 1f)
        buttonClick2.duration = 50
        buttonClick2.fillAfter = true
        buttonClick2.startOffset = 70

        binding.textData.setOnClickListener {

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            showDialogSetDate()

        }

        binding.imageDate.setOnClickListener {

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            showDialogSetDate()

        }

        binding.textSetDate.setOnClickListener{

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            showDialogSetDate()

        }

    }

    private fun showDialogSetDate() {    // показывает диалоговое окно для выбора  даты

        dialogScrollDate = DialogScrollDateBinding.inflate(layoutInflater)
        dialog = Dialog(this.activity!!)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogScrollDate.root)
        dialog.setCancelable(true)

        dialogScrollDate.numberPicker.minValue = 1
        dialogScrollDate.numberPicker.maxValue = 31
        dialogScrollDate.numberPicker.value = 1
        dialogScrollDate.numberPicker.wrapSelectorWheel = false

        var res = 1  // день начала учета

        dialogScrollDate.numberPicker.setOnValueChangedListener{picker, oldVal, newVal ->
            res = newVal
        }

        dialogScrollDate.dialogBtn.setOnClickListener {

            saveData(getString(R.string.setDateDay), res)
            dialog.dismiss()
            binding.textSetDate.text = pref.getInt(getString(R.string.setDateDay), 1).toString()

        }

        hidingPanel(dialog)
        dialog.show()

    }

    private fun switchChecked() {        // включение заставки

        // параметры анимации нажатия
        val buttonClick1 = AlphaAnimation(1f, 0.7f)
        buttonClick1.duration = 160
        buttonClick1.fillAfter = false
        val buttonClick2 = AlphaAnimation(0.7f, 1f)
        buttonClick2.duration = 50
        buttonClick2.fillAfter = true
        buttonClick2.startOffset = 70


        binding.textSpashScreen.setOnClickListener {

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            when (binding.switchSplashScreen.isChecked) {
                true -> {
                    binding.switchSplashScreen.isChecked = false
                    saveData(getString(R.string.flagSplashScreen), 0)
                }
                else -> {
                    binding.switchSplashScreen.isChecked = true
                    saveData(getString(R.string.flagSplashScreen), 1)
                }
            }
        }

        binding.imageSplashScreen.setOnClickListener {

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            when (binding.switchSplashScreen.isChecked) {
                true -> {
                    binding.switchSplashScreen.isChecked = false
                    saveData(getString(R.string.flagSplashScreen), 0)
                }
                else -> {
                    binding.switchSplashScreen.isChecked = true
                    saveData(getString(R.string.flagSplashScreen), 1)
                }
            }
        }

        binding.switchSplashScreen.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                saveData(getString(R.string.flagSplashScreen), 1)
            }else{
                saveData(getString(R.string.flagSplashScreen), 0)
            }
        }
    }

    private fun buttonToClear() {    // TODO(УБРАТЬ И ПЕРЕДЕЛАТЬ)
        // TODO(ДОБАВИТЬ УДАЛЕНИЕ БД)

        // параметры анимации нажатия
        val buttonClick1 = AlphaAnimation(1f, 0.7f)
        buttonClick1.duration = 160
        buttonClick1.fillAfter = false
        val buttonClick2 = AlphaAnimation(0.7f, 1f)
        buttonClick2.duration = 50
        buttonClick2.fillAfter = true
        buttonClick2.startOffset = 70

        binding.clearPref.setOnClickListener {

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            // TODO(нужно сделать переброс на диалоговое окно)
            clearPref()

        }

        binding.imageClearPref.setOnClickListener{

            it.startAnimation(buttonClick1)
            it.startAnimation(buttonClick2)
            it.visibility = View.VISIBLE

            // TODO(нужно сделать переброс на диалоговое окно)
            clearPref()

        }

    }

    private fun clearPref() {    // очищает переменные в памяти
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveData(key: String, dataToSave: Int) {

        val editor = pref.edit()
        editor?.putInt(key, dataToSave)
        editor?.apply()

    }
}