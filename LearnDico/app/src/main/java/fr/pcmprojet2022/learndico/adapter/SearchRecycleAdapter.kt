package fr.pcmprojet2022.learndico.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import fr.pcmprojet2022.learndico.data.entites.Words
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.pcmprojet2022.learndico.databinding.ItemWordBinding
import fr.pcmprojet2022.learndico.fragment.ListFragmentDirections
import fr.pcmprojet2022.learndico.fragment.SearchFragmentDirections


class SearchRecycleAdapter(private val words: MutableList<Words>, private val context: Context) :

    RecyclerView.Adapter<SearchRecycleAdapter.VH>() {

    class VH(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var wordObj: Words
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = VH(binding)

        holder.binding.delete.setOnClickListener {
            Log.wtf("click","TODO: delete")
            MaterialAlertDialogBuilder(context)
                .setTitle("Supprimer le mot")
                .setMessage("Êtes vous sûr de bien vouloir supprimer ce mot?")
                .setNegativeButton("Non") { dialog, which ->
                    //TODO: Respond to negative button press
                }
                .setPositiveButton("Oui") { dialog, which ->
                    //TODO: Respond to positive button press
                }
                .show()
        }

        holder.binding.edit.setOnClickListener {
            Log.wtf("click","TODO: edit")
            val direction = ListFragmentDirections.actionListFragmentToEditWordFragment()
            parent.findNavController().navigate(direction)
        }

        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.wordObj = words[position]

        with(holder.binding){
            word.text = holder.wordObj.word
            translation.text = holder.wordObj.translation
            translationSignification.text = holder.wordObj.translationSignification
            wordSignification.text = holder.wordObj.wordSignification
        }

    }

    override fun getItemCount(): Int = words.size

}