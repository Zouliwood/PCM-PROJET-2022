package fr.pcmprojet2022.learndico

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Words
import fr.pcmprojet2022.learndico.databinding.ActivitySaveBinding
import kotlin.concurrent.thread


class SauvegardeActivity : AppCompatActivity() {

    /**
     * Class SauvegardeActivity est l'activité ou l'utilisateur peut ajouter un mot
     * Elle n'est accessible qu'en partageant un lien
     */
    private val database by lazy{ LearnDicoBD.getInstanceBD(this)}

    private lateinit var binding: ActivitySaveBinding
    private var url: String? = ""
    private var langueSrc: String = ""
    private var langueDest: String = ""
    private var motSrc: String = ""
    private var motDest: String = ""
    private var descSrc: String = ""
    private var descDst: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadValueFromBundle(savedInstanceState)
        laodBundleValue()
        if(intent.action.equals( "android.intent.action.SEND")){
            url = intent.extras?.getString( "android.intent.extra.TEXT" )
        }else{
            Toast.makeText(this, R.string.erreur_survenue, Toast.LENGTH_LONG).show()
            finish()
        }
        buttonEventClick()
    }

    private fun buttonEventClick(){
        binding.validerId.setOnClickListener {
            ajouterMot()
        }
    }
    private fun ajouterMot() {
          if(argsIsOk()){

              with(binding) {
                  var descriptionOrigine = descriptionTradId.text.toString().replace(" ", "")
                  var descriptionTrad = binding.descriptionOrgineId.text.toString().replace(" ", "")

                  if (descriptionOrigine.isBlank()) descriptionOrigine = "Aucune description"
                  if (descriptionTrad.isBlank()) descriptionTrad = "Aucune description"

                  val mot = Words(
                      binding.saveWordOrigineId.text.toString().lowercase(),
                      binding.wordTradId.text.toString().replace(" ", ""),
                      binding.saveLangueSrcId.text.toString().replace(" ", ""),
                      binding.saveLangueDstId.text.toString().replace(" ", ""),
                      descriptionOrigine,
                      descriptionTrad,
                      url.toString().replace(" ", "").lowercase(),
                      "",
                      4
                  )
                  addWord(mot)
                  /* Traitement de l'url des dicos et du nom du dictionnaire */
                  val tabDicoUrl = url.toString().lowercase().replace("https://www.", "").replace("https://", "").split(".")
                  var locationNameDico = 0
                  if(tabDicoUrl.size > 2 && (tabDicoUrl[0].length <= 3 || tabDicoUrl[0].contains("dictionnary") || tabDicoUrl[0].contains("mobile") || tabDicoUrl[0].contains("dictionnaire"))){
                      locationNameDico = 1
                  }
                  val urlTab = url.toString().lowercase().split("/")
                  var newUrl = ""
                  for(urlParse in urlTab){
                      newUrl += "$urlParse/"
                      if(urlParse == binding.saveWordOrigineId.text.toString().trim()){
                          break
                      }
                  }
                  val nomDico =tabDicoUrl[locationNameDico].replaceFirstChar { c -> c.uppercase() }
                  val urlDico = newUrl.lowercase()
                      .replace(binding.saveWordOrigineId.text.toString().lowercase().trim(), "%mot_origine%")
                      .replace(binding.wordTradId.text.toString().lowercase().trim(), "%mot_trad%")
                  addDictionnaire(urlDico, nomDico)
              }
              Toast.makeText(this, R.string.nouveauMotToList, Toast.LENGTH_LONG).show()
              startActivity(Intent(this, MainActivity::class.java))
              finish()
          }else{
              Toast.makeText(this, R.string.invalideChamps, Toast.LENGTH_SHORT).show()
          }
    }

    private fun argsIsOk(): Boolean {
        return (binding.saveLangueSrcId.text.toString().isNotEmpty()&&
                binding.saveLangueDstId.text.toString().isNotEmpty() &&
                binding.wordTradId.text.toString().isNotEmpty() &&
                binding.saveWordOrigineId.text.toString().isNotEmpty())
    }

    private fun addDictionnaire(urlDico: String, nomDico: String){
        thread {
            (database.getRequestDao().insertDictionnaire(
                Dico(
                    nomDico,
                    urlDico,
                    binding.saveLangueSrcId.text.toString().lowercase()
                        .replace(" ", ""),
                    binding.saveLangueDstId.text.toString().lowercase()
                        .replace(" ", "")
                )
            ))
        }
    }

    private fun addWord(words: Words) {
        thread {
            thread { database.getRequestDao().insertMot(words)}
        }
    }

    private fun loadValueFromBundle(savedInstanceState: Bundle?){
        langueSrc = savedInstanceState?.getString("langueSrc") ?: ""
        langueDest = savedInstanceState?.getString("langueDest")?: ""
        motSrc = savedInstanceState?.getString("MotSrc")?: ""
        motDest = savedInstanceState?.getString("MotDest")?: ""
        descSrc = savedInstanceState?.getString("DescSrc")?: ""
        descDst = savedInstanceState?.getString("DescDest")?: ""
    }
    private fun laodBundleValue() {
        with(binding) {
            saveLangueSrcId.setText(langueSrc)
            saveLangueDstId.setText(langueDest)
            saveWordOrigineId.setText(motSrc)
            wordTradId.setText(motDest)
            descriptionOrgineId.setText(descSrc)
            descriptionTradId.setText(descDst)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("langueSrc", langueSrc)
        outState.putString("langueDest", langueDest)
        outState.putString("MotSrc", motSrc)
        outState.putString("MotDest", motDest)
        outState.putString("DescSrc", descSrc)
        outState.putString("DescDest", descDst)
    }
}