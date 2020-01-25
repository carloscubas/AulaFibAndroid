package br.aula.agendaaula

import android.app.DatePickerDialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import br.aula.db.Contato
import br.aula.db.ContatoRepository
import kotlinx.android.synthetic.main.activity_contato.*
import java.text.SimpleDateFormat
import java.util.*

class ContatoActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var contato: Contato? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent?.getSerializableExtra("contato") != null) {
            contato = intent?.getSerializableExtra("contato") as Contato
            txtNome?.setText(contato?.nome)
            txtEndereco?.setText(contato?.endereco)
            txtTelefone?.setText(contato?.telefone?.toString())
            //dataNascimento = cal.timeInMillis,
            txtEmail?.setText(contato?.email)
            txtSite?.setText(contato?.email)
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        txtDatanascimento.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@ContatoActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        btnCadastro.setOnClickListener {
            val contato = Contato(
                nome = txtNome?.text.toString(),
                endereco = txtEndereco?.text.toString(),
                telefone = txtTelefone?.text.toString().toLong(),
                dataNascimento = cal.timeInMillis,
                email = txtEmail?.text.toString(),
                site = txtSite?.text.toString())

            ContatoRepository(this).create(contato)
            finish()
        }
    }




    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtDatanascimento.text = sdf.format(cal.getTime())
    }
}
