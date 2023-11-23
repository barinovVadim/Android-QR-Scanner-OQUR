package com.example.wym_002.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.wym_002.R
import com.example.wym_002.databinding.CustomCashDialogLayoutBinding
import com.example.wym_002.databinding.CustomDialogLayoutBinding
import com.example.wym_002.databinding.FragmentMainFragmentBinding
import com.example.wym_002.hidingPanel
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainFragmentBinding
    private lateinit var dialog: Dialog
    private lateinit var customCashDialog: CustomCashDialogLayoutBinding
    private lateinit var customDialog: CustomDialogLayoutBinding
    lateinit var pref: SharedPreferences

    var toast: Toast? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainFragmentBinding.inflate(layoutInflater)

        pref = context!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        // resultCardBalance        key: R.drawable.credit_card_white.toString()
        // resultWalletBalance      key: R.drawable.wallet_white.toString()
        // resultBankBalance        key: R.drawable.account_balance_white.toString()
        // resultTotalBalance       key: getString(R.string.keyTotalBalance)
        // checkSplashScreen        key: getString(R.string.flagSplashScreen)      ОБРАТНЫЕ ПЕРЕМЕННЫЕ
        //                                                                        0 == TRUE   1 == FALSE
        // setDateDay               key: getString(R.string.setDateDay)


        updatingVariables()

        attachViewDragListenerAllIems()

        draggingElements()

        return binding.root
    }


    // TODO("нужно сделать рабочие прогресс бары и чтобы считались лимиты")


    private fun attachViewDragListenerAllIems() {
        // В ВИДЕ КЛЮЧА ПЕРЕДАЕТ ИКОНКУ В БЕЛОМ ЦВЕТЕ
        attachViewDragListener(binding.btnCard,
            R.drawable.credit_card,
            R.drawable.credit_card_white
        )
        attachViewDragListener(binding.btnWallet,
            R.drawable.wallet,
            R.drawable.wallet_white)
        attachViewDragListener(binding.btnBank,
            R.drawable.account_balance,
            R.drawable.account_balance_white
        )
    }


    private fun draggingElements() {
        startFunc(binding.house, R.drawable.house_white)
        startFunc(binding.bus, R.drawable.bus_white)
        startFunc(binding.foodHouse, R.drawable.food_house_white)
        startFunc(binding.health, R.drawable.health_white)
        startFunc(binding.coffee, R.drawable.coffee_white)
        startFunc(binding.games, R.drawable.games_white)
        startFunc(binding.clothes, R.drawable.clothes_white)
        startFunc(binding.another, R.drawable.another_white)
    }


    private fun updatingVariables() {       // обновляет счетчики на экране

        val resultCardBalance = pref.getInt(R.drawable.credit_card_white.toString(), 0)
        val resultWalletBalance = pref.getInt(R.drawable.wallet_white.toString(), 0)
        val resultBankBalance = pref.getInt(R.drawable.account_balance_white.toString(), 0)

        val resultTotalBalance = pref.getInt(getString(R.string.keyTotalBalance), 0)

        binding.cardBalance.text = resultCardBalance.toString()
        binding.walletBalance.text = resultWalletBalance.toString()
        binding.bankBalance.text = resultBankBalance.toString()

        // TODO(ДОПИСАТЬ ПОДСЧЕТ ЛИМИТОВ чтобы менялись при выходе за рамки и тд)
        binding.mainPr.text = (resultTotalBalance * 0.5).toInt().toString()
        binding.secondaryPr.text = (resultTotalBalance * 0.3).toInt().toString()
        binding.spendPr.text = (resultTotalBalance * 0.2).toInt().toString()

    }


    private fun showToastMsg(string: String) {

        val container = this.activity!!.findViewById<ViewGroup>(R.id.custom_toast_container)
        val layout = layoutInflater.inflate(R.layout.custom_toast, container)
        val text: TextView = layout.findViewById(R.id.text)
        text.text = string

        if (toast != null){
            toast!!.cancel()
        }
        toast = Toast(this.activity!!.applicationContext)
        toast!!.duration = Toast.LENGTH_SHORT
        toast!!.view = layout
        toast!!.show()

    }

    private fun startFunc(image: ImageView, drawableIcon: Int){

        fun showCustomDialog(balance: TextView, drawableIconKey: Int) {

            customDialog = CustomDialogLayoutBinding.inflate(layoutInflater)
            customDialog.iconOnDialog.setImageResource(drawableIcon)
            dialog = Dialog(this.activity!!)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(customDialog.root)
            dialog.setCancelable(true)

            val calendar = Calendar.getInstance()

            // указывается дата по умолчанию
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val textDate = "Дата: ${dateFormat.format(calendar.time)}"
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val textTime = "Время: ${timeFormat.format(calendar.time)}"
            customDialog.enterData.text = "$textDate        $textTime"

            customDialog.enterData.setOnClickListener {
                showDataTimePicker()
            }

            customDialog.dialogBtn.setOnClickListener {

                val res = customDialog.enterSum.text.toString()   // сумма траты
                val spend = customDialog.enterCat.text.toString() // категория траты

                if (res.isNotEmpty()) {
                    if(Integer.parseInt(res) != 0){
                        if (Integer.parseInt(balance.text.toString()) >= Integer.parseInt(res)) {
                            if (spend.isNotEmpty()) {       // TODO(сделать добавление в бд)

                                val dataToSave = Integer.parseInt(balance.text.toString()) - Integer.parseInt(res)
                                saveData(drawableIconKey.toString(), dataToSave)

                                dialog.dismiss()

                            } else showToastMsg(getString(R.string.plsEnterSpend))
                        }else showToastMsg(getString(R.string.plsNoMoney))
                    }else showToastMsg(getString(R.string.plsEnterSumCorrect))
                }else showToastMsg(getString(R.string.plsEnterSum))

                updatingVariables()
            }

            hidingPanel(dialog)
            dialog.show()

        }

        // проверяет и вызывает окно для добавления траты
        fun checkIfItemOnCat(dragEvent: DragEvent, draggableItem: View) {

            val catXStart = binding.house.x
            val catYStart = binding.house.y

            val catXEnd = catXStart + binding.house.width
            val catYEnd = catYStart + binding.house.height

            if (dragEvent.x in catXStart..catXEnd && dragEvent.y in catYStart..catYEnd){
                // проверка на то, откуда потратил
                when (draggableItem) {
                    binding.btnCard -> showCustomDialog(binding.cardBalance,
                        R.drawable.credit_card_white
                    )
                    binding.btnWallet -> showCustomDialog(binding.walletBalance,
                        R.drawable.wallet_white
                    )
                    binding.btnBank -> showCustomDialog(binding.bankBalance,
                        R.drawable.account_balance_white
                    )
                }
            }

        }

        val maskDragListener = View.OnDragListener { view, dragEvent ->

            val draggableItem = dragEvent.localState as View

            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    image.alpha = 0.3f          // затемняет картинку еды
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    // возвращает в начальное положение категорию, если иконка не в нужной зоне
                    image.alpha = 1.0f
                    draggableItem.visibility = View.INVISIBLE
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DROP -> {
                    // возвращает категорию, если иконку отпустили
                    image.alpha = 1.0f

                    //возвращает иконку на место
                    if (dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        val draggedData = dragEvent.clipData.getItemAt(0).text
                        println("draggedData $draggedData")
                    }
                    checkIfItemOnCat(dragEvent, draggableItem)
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    draggableItem.visibility = View.VISIBLE
                    view.invalidate()
                    true
                }
                else -> {
                    false
                }

            }
        }

        image.setOnDragListener(maskDragListener)

    }

    private fun showDataTimePicker(){

        val selectedDate = Calendar.getInstance()
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this.activity!!, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->

                val timePickerDialog = TimePickerDialog(
                    this.activity!!, { TimePicker, hourOfDay: Int, minute: Int ->
                        selectedDate.set(year, monthOfYear, dayOfMonth, hourOfDay, minute)
                        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        val textDate = "Дата: ${dateFormat.format(selectedDate.time)}"
                        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val textTime = "Время: ${timeFormat.format(selectedDate.time)}"
                        customDialog.enterData.text = "$textDate        $textTime"
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                )

                hidingPanel(timePickerDialog)
                timePickerDialog.show()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        hidingPanel(datePickerDialog)
        datePickerDialog.show()

    }

    @SuppressLint("ClickableViewAccessibility")      // слушатель движений
    private fun attachViewDragListener(imageButton: ImageButton, drawable: Int, drawableIcon: Int) {

        // параметры анимаций принажатии и отпускании
        val buttonClick1 = AlphaAnimation(1f, 0f)
        buttonClick1.duration = 160
        buttonClick1.fillAfter = false
        val buttonClick2 = AlphaAnimation(0.5f, 1f)
        buttonClick2.duration = 50
        buttonClick2.fillAfter = true
        buttonClick2.startOffset = 70

        // считыватель касаний
        imageButton.setOnTouchListener { view: View, event: MotionEvent ->

            if (event.action == MotionEvent.ACTION_DOWN) {
                view.startAnimation(buttonClick1)

                view.tag = true
            }

            // проверка на зажатие
            if (view.isPressed && view.tag as Boolean && event.action == MotionEvent.ACTION_MOVE) {

                val eventDuration = event.eventTime - event.downTime + 250
                if (eventDuration >= ViewConfiguration.getLongPressTimeout()) {

                    view.tag = true

                    val item = ClipData.Item(R.string.dragMessage.toString())

                    val dataToDrag = ClipData(
                        R.string.dragMessage.toString(),
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        item
                    )

                    val maskShadow = DragShadowBuilder(view, drawable)

                    // если зажата, то двигается
                    view.startDragAndDrop(dataToDrag, maskShadow, view, 0)

                    view.visibility = View.INVISIBLE
                    true
                }
                // иначе срабатывает слушатель нажатий
                else {
                    imageButton.setOnClickListener {

                        showCustomCashDialog(drawableIcon)
                        view.startAnimation(buttonClick2)
                        it.visibility = View.VISIBLE
                    }
                    true
                }


            }
            else {
                false
            }


        }
    }


    private fun showCustomCashDialog(drawableIcon: Int) {

        customCashDialog = CustomCashDialogLayoutBinding.inflate(layoutInflater)
        customCashDialog.iconOnDialog.setImageResource(drawableIcon)
        dialog = Dialog(this.activity!!)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(customCashDialog.root)
        dialog.setCancelable(true)

        customCashDialog.dialogBtn.setOnClickListener {

            val res: String = customCashDialog.enterSum.text.toString()
            if (res.isNotEmpty()) {

                if (Integer.parseInt(res) != 0) {
                    val past = pref.getInt(drawableIcon.toString(), 0)
                    val pastTotal = pref.getInt(getString(R.string.keyTotalBalance), 0)
                    saveData(drawableIcon.toString(), (past + Integer.parseInt(res)))

                    // добавляет в начальный баланс новые финансы
                    saveData(getString(R.string.keyTotalBalance), (pastTotal + Integer.parseInt(res)))

                    dialog.dismiss()

                }else showToastMsg(getString(R.string.plsEnterSumCorrect))

            }else showToastMsg(getString(R.string.plsEnterSum))

            updatingVariables()
        }

        hidingPanel(dialog)
        dialog.show()

    }


    @SuppressLint("CommitPrefEdits")
    private fun saveData(key: String, dataToSave: Int) {

        val editor = pref.edit()
        editor?.putInt(key, dataToSave)
        editor?.apply()

    }


}

// билдер теней
private class DragShadowBuilder(view: View, drawable: Int) : View.DragShadowBuilder(view) {

    private val shadow = ResourcesCompat.getDrawable(view.context.resources, drawable, view.context.theme)

    override fun onProvideShadowMetrics(size: Point, touch: Point) {

        val width: Int = view.width * 6 / 10
        val height: Int = view.height * 6 / 10

        shadow?.setBounds(0, 0, width, height)
        size.set(width, height)
        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {
        shadow?.draw(canvas)
    }

}