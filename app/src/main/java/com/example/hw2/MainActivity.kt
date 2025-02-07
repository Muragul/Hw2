package com.example.hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    lateinit var input: TextView
    lateinit var oper: TextView
    var a: Double = 0.0
    var b: Double = 0.0
    var res: Double = 0.0
    var error: String = ""
    var mono: String = ""
    var bi: String = ""
    var sec: Boolean = false
    var complete: Boolean = false
    var entermode: Int = 1
    var f: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input)
        oper = findViewById(R.id.oper)
        val b0: Button = findViewById(R.id.b0)
        val b1: Button = findViewById(R.id.b1)
        val b2: Button = findViewById(R.id.b2)
        val b3: Button = findViewById(R.id.b3)
        val b4: Button = findViewById(R.id.b4)
        val b5: Button = findViewById(R.id.b5)
        val b6: Button = findViewById(R.id.b6)
        val b7: Button = findViewById(R.id.b7)
        val b8: Button = findViewById(R.id.b8)
        val b9: Button = findViewById(R.id.b9)

        b0.setOnClickListener {
            buttonClicked(0)
        }
        b1.setOnClickListener {
            buttonClicked(1)
        }
        b2.setOnClickListener {
            buttonClicked(2)
        }
        b3.setOnClickListener {
            buttonClicked(3)
        }
        b4.setOnClickListener {
            buttonClicked(4)
        }
        b5.setOnClickListener {
            buttonClicked(5)
        }
        b6.setOnClickListener {
            buttonClicked(6)
        }
        b7.setOnClickListener {
            buttonClicked(7)
        }
        b8.setOnClickListener {
            buttonClicked(8)
        }
        b9.setOnClickListener {
            buttonClicked(9)
        }

        val bsqrt: Button = findViewById(R.id.bsqrt)
        val bsqr: Button = findViewById(R.id.bsqr)
        val bper: Button = findViewById(R.id.bper)
        val bclear: Button = findViewById(R.id.bclear)
        val bsum: Button = findViewById(R.id.bsum)
        val bdif: Button = findViewById(R.id.bdif)
        val bmult: Button = findViewById(R.id.bmult)
        val bdiv: Button = findViewById(R.id.bdiv)
        val bdel: Button = findViewById(R.id.bdel)
        val bdot: Button = findViewById(R.id.bdot)
        val bres: Button = findViewById(R.id.bres)

        bsqrt.setOnClickListener {
            monoFunctionClicked("sqrt")
            oper.text = "sqrt("
        }
        bsqr.setOnClickListener {
            monoFunctionClicked("sqr")
            oper.text = "sqr("
        }
        bper.setOnClickListener {
            monoFunctionClicked("per")
            oper.text = "%"
        }
        bsum.setOnClickListener {
            biFunctionClicked("sum")
            oper.text = "+"
        }
        bdif.setOnClickListener {
            biFunctionClicked("dif")
            oper.text = "-"
        }
        bmult.setOnClickListener {
            biFunctionClicked("mult")
            oper.text = "*"
        }
        bdiv.setOnClickListener {
            biFunctionClicked("div")
            oper.text = "/"
        }

        bclear.setOnClickListener {
            input.text = "0"
            a = 0.0
            b = 0.0
            res = 0.0
            error = ""
            sec = false
            complete = false
            mono = ""
            bi = ""
            oper.text = ""
            entermode = 1
        }
        bdel.setOnClickListener {
            if (isWord()){
                input.text = "0"
            } else {
                if (input.text.contains("-") && input.text.length==2){
                    input.text="0"
                } else if (input.text.length != 1){
                    var s = input.text.toString()
                    var s1 = s.substring(0, s.length - 1)
                    input.text = s1
                } else {
                    input.text = "0"
                    oper.text = ""
                }
            }
            if (entermode == 1)
                a = input.text.toString().toDouble()
            else
                b = input.text.toString().toDouble()
        }
        bdot.setOnClickListener {
                if (!input.text.contains(".") && !isWord()) {
                    val s = input.text.toString() + "."
                    input.text = s
                }
        }

        bres.setOnClickListener {
            if (entermode == 1 && input.text.contains(".")) {
                if (input.text == "0.0")
                    input.text = "0"
                else
                    while (input.text[input.text.length - 1] == '0') {
                        var s: String = input.text.toString()
                        var s1: String = s.substring(0, s.length - 1)
                        if (s1[s1.length - 1] == '.')
                            s1 = s1.substring(0, s1.length - 1)
                        input.text = s1
                    }
            }
            if (bi != "") {
                biFunction(bi)
                if (error != "") {
                    input.text = error
                    error = ""
                } else {
                    if (isPossibleToConvert(res)) {
                        var s = res.toString().substring(0, res.toString().length - 2)
                        input.text = s
                    } else
                        input.text = res.toString()
                }
                a = res
                entermode = 1
                complete = true
                sec = false
            }
        }
    }

    fun monoFunction(operation: String) {
        val k: Double
        if (entermode == 1) {
            k = a
        } else {
            k = b
        }
        when (operation) {
            "sqr" -> res = k * k
            "sqrt" -> if (k > 0) {
                res = sqrt(k)
            } else {
                error = "impossible"
                oper.text = ""
            }
            "per" -> res = k / 100;
        }
        if (entermode == 1) {
            a = res
        } else {
            b = res
        }
    }

    fun biFunction(operation: String) {
        when (operation) {
            "sum" -> res = a + b
            "dif" -> res = a - b
            "mult" -> res = a * b
            "div" -> if (b != 0.0) {
                res = a / b
            } else {
                error = "impossible"
                oper.text = ""
            }
        }
    }

    fun isWord(): Boolean {
        when (input.text.toString()) {
            "" -> return true
            "impossible" -> return true
            "NaN" -> return true
            "Infinity" -> return true
        }
        if (input.text.toString().contains('E'))
            return true
        return false
    }

    fun isPossibleToConvert(res: Double): Boolean {
        if (res % 1 == 0.0) return true
        return false
    }

    fun buttonClicked(n: Int) {
        var s: String = n.toString()
        if (n == 0 && input.text.toString() == "0") {
        } else {
            if (input.text.toString() == "0" || isWord() || complete) {
                input.text = s
                complete = false
            } else {
                val k: String = input.text.toString()
                input.text = k + s
            }
            if (bi != "") {
                sec = true
            }
            if (entermode == 1) {
                a = input.text.toString().toDouble()
            } else {
                b = input.text.toString().toDouble()
            }
        }
    }

    fun monoFunctionClicked(operation: String) {
        if (isWord()){
            input.text = "0"
        }
        monoFunction(operation)
        if (error != "") {
            input.text = error
            error = ""
        } else {
            if (isPossibleToConvert(res)) {
                var s: String = res.toString().substring(0, res.toString().length - 2)
                input.text = s
            } else
                input.text = res.toString()
        }
        complete = true
    }

    fun biFunctionClicked(operation: String) {
        if (isWord()){
            input.text = "0"
        }
        if (entermode == 1 && input.text.toString().contains(".")) {
            if (input.text == "0.0"){
                input.text = "0"
            }
            else
                while (input.text[input.text.toString().length - 1] == '0') {
                    val s = input.text.toString()
                    val s1 = s.substring(0, s.length - 1)
                    input.text = s1
                }
        }
        entermode = 2
        b = input.text.toString().toDouble()
        if (bi != "" && sec) {
            biFunction(bi)
            if (error != "") {
                input.text = error
                error = ""
            } else {
                if (isPossibleToConvert(res)) {
                    var s: String = res.toString().substring(0, res.toString().length - 2)
                    input.text = s
                } else {
                    input.text = res.toString()
                }
            }
            a = res
            b = res
        }
        bi = operation
        complete = true
        sec = false
    }
}
