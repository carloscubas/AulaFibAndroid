package br.aula.db

import android.content.Context
import br.aula.db.ConstantsDb.CONTATOS_DB_TABLE
import org.jetbrains.anko.db.*
import timber.log.Timber

class ContatoRepository(val context: Context) {

    fun getOne(id: Int) : Contato = context.database.use {
        val contatos: Contato

        select(CONTATOS_DB_TABLE, "id", "email", "endereco", "nome", "telefone", "datanascimento", "site", "foto")
            .whereArgs( "id = {id}", "id" to id)
            .parseSingle(object: MapRowParser<Contato> {
                override fun parseRow(columns: Map<String, Any?>): Contato {
                    return Contato(
                        id = columns.getValue("id").toString()?.toLong(),
                        foto = columns.getValue("foto")?.toString(),
                        nome = columns.getValue("nome")?.toString(),
                        endereco = columns.getValue("endereco")?.toString(),
                        telefone = columns.getValue("telefone")?.toString()?.toLong(),
                        dataNascimento = columns.getValue("datanascimento")?.toString()?.toLong(),
                        email = columns.getValue("email")?.toString(),
                        site = columns.getValue("site")?.toString())
                }
            })
    }

    fun findAll() : ArrayList<Contato> = context.database.use {
        val contatos = ArrayList<Contato>()

        select(CONTATOS_DB_TABLE, "id", "email", "endereco", "nome", "telefone", "datanascimento", "site", "foto")

            .parseList(object: MapRowParser<List<Contato>> {
                override fun parseRow(columns: Map<String, Any?>): List<Contato> {
                    val contato = Contato(
                        id = columns.getValue("id").toString()?.toLong(),
                        foto = columns.getValue("foto")?.toString(),
                        nome = columns.getValue("nome")?.toString(),
                        endereco = columns.getValue("endereco")?.toString(),
                        telefone = columns.getValue("telefone")?.toString()?.toLong(),
                        dataNascimento = columns.getValue("datanascimento")?.toString()?.toLong(),
                        email = columns.getValue("email")?.toString(),
                        site = columns.getValue("site")?.toString())
                    contatos.add(contato)
                    return contatos
                }
            })

        contatos
    }


    fun create(contato: Contato) = context.database.use {
        insert(CONTATOS_DB_TABLE,
            "foto" to contato.foto,
            "nome" to contato.nome,
            "endereco" to contato.endereco,
            "telefone" to contato.telefone,
            "email" to contato.email,
            "site" to contato.site,
            "dataNascimento" to contato.dataNascimento)
    }


    fun update(contato: Contato) = context.database.use {
        val updateResult = update(CONTATOS_DB_TABLE,
            "foto" to contato.foto,
            "nome" to contato.nome,
            "endereco" to contato.endereco,
            "telefone" to contato.telefone,
            "email" to contato.email,
            "site" to contato.site)
            .whereArgs("id = {id}","id" to contato.id).exec()

        Timber.d("Update result code is $updateResult")
    }


    fun delete(id: Int) = context.database.use {
        delete(CONTATOS_DB_TABLE, whereClause
        = "id = {contatoId}", args = *arrayOf("contatoId" to id))
    }

}
