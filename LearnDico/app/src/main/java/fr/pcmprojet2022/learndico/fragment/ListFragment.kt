package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import fr.pcmprojet2022.learndico.SelectLangDialogFragment
import fr.pcmprojet2022.learndico.adapter.SearchRecycleAdapter
import fr.pcmprojet2022.learndico.data.Mot
import fr.pcmprojet2022.learndico.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        //TODO: remove
        val lst: MutableList<Mot> = listOf(
            Mot(
                "Avion",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
            ),
            Mot(
                "Avion",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
            ),
            Mot(
                "Avion",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
            ),
            Mot(
                "Avion",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
            ),
            Mot(
                "Avion",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
            )
        ) as MutableList<Mot>

        recyclerView = binding.recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = SearchRecycleAdapter(lst)

        binding.buttonMoreVert.setOnClickListener {
            showDialogFragment()
        }

        return view
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListFragmentDirections.actionListFragmentToEditWordFragment()
    }*/

    private fun showDialogFragment() {

        val chip = Chip(context)
        chip.text = "ABC"
        chip.isCloseIconVisible = true


        printDialogOrToast()

    }

    private fun printDialogOrToast(){
        //TODO: print toast if src or dest empty
        SelectLangDialogFragment().show(childFragmentManager, SelectLangDialogFragment.TAG)
    }

}