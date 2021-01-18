package br.com.creativedevmind.padv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdvogadoListaActivity : AppCompatActivity() {
    private val novoProdutoRequestCode = 1
    private lateinit var mainViewModel: AdvogadoListaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_advogado)

        val recyclerView = findViewById<RecyclerView>(R.id.rvAdvogados)
        val adapter = AdvogadoListaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel = ViewModelProvider(this).get(AdvogadoListaViewModel::class.java)
        mainViewModel.advogados.observe(this, Observer { advogados ->
            // Update the cached copy of the words in the adapter.
            advogados?.let { adapter.setAdvogados(it) }
        })

//        val fabNovoProduto = findViewById<FloatingActionButton>(R.id.fabNovoProduto)
//        fabNovoProduto.setOnClickListener {
//            val intent = Intent(this@MainActivity, NovoProdutoActivity::class.java)
//            startActivityForResult(intent, novoProdutoRequestCode)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == novoProdutoRequestCode && resultCode == Activity.RESULT_OK) {
//            data?.getStringExtra(NovoProdutoActivity.EXTRA_REPLY)?.let {
//                val advogado = Advogado(it)
//                mainViewModel.insert(advogado)
//            }
//        } else {
            Toast.makeText(
                applicationContext,
                "Nenhum produto informado",
                Toast.LENGTH_LONG
            ).show()
//        }
    }
}