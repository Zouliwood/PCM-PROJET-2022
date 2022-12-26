package fr.pcmprojet2022.learndico.sharedviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import fr.pcmprojet2022.learndico.LearnDicoApplication
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.data.entites.Words
import kotlin.concurrent.thread

class DaoViewModel (application: Application): AndroidViewModel(application) {

    /**
     * Cette classe de type ViewModel permet de charger les dictionnaires dans le fragment de séléction
     * des dictionnaires. Cela implique une connexion à room
     *
     * Documentation sur les ViewModel
     * https://developer.android.com/codelabs/basic-android-kotlin-training-shared-viewmodel#2
     */

    /* Connexion à room & requetes */
    private val dao = (application as LearnDicoApplication).getDataBase.getRequestDao()
    private val allDicoBD = MutableLiveData<List<Dico>>(emptyList())
    private val allLanguagesBd = MutableLiveData<List<Langues>>(emptyList())
    private val allWordsBd = MutableLiveData<List<Words>>(emptyList())
    private val languesSelected: MutableList<Langues> = mutableListOf<Langues>()
    private val resultPartialWord = MutableLiveData<List<Words>>(emptyList())
    private val updateFileName = MutableLiveData(0)
    private val availableNotif = MutableLiveData<List<Words>>(emptyList())

    fun loadAllDico() {
        thread {
            allDicoBD.postValue(dao.loadAllDico())
        }
    }

    fun loadAllLangues() {
        thread {
            allLanguagesBd.postValue(dao.loadAllLanguages())
        }
    }

    fun insertLangues(langues: Langues) {
        thread {
            dao.insertLangues(langues)
        }
    }

    fun loadLanguages(languages: String){
        thread {
            languesSelected.addAll(dao.loadLanguages(languages))
        }
    }

    fun addFileName(word : Words){
        thread {
            val i = dao.updateWord(word)
            updateFileName.postValue(i)
        }
    }

    fun getAllDicoBD() : MutableLiveData<List<Dico>>{
        return allDicoBD
    }

    fun getAllLanguagesBD() : MutableLiveData<List<Langues>>{
        return allLanguagesBd
    }

    fun getUpdateFileName() : MutableLiveData<Int>{
        return updateFileName
    }

    fun getLanguesSelected():MutableList<Langues>{
        return languesSelected
    }

    fun getAllWordBD() : MutableLiveData<List<Words>> {
        return allWordsBd
    }


    fun getResultPartialWord() : MutableLiveData<List<Words>> {
        return resultPartialWord
    }

    fun getAvailableNotif() : MutableLiveData<List<Words>> = availableNotif

    fun loadAllWord() {
        thread {
            allWordsBd.postValue(dao.loadAllWords())
        }
    }

   /* fun insertWord() {
        thread {
            //val wordOrigin: String, val wordTranslate: String, val languageOrigin: String, val languageTranslation: String, val wordSignification: String, val translationSignification: String, val url: String
            dao.insertMot(Words("Mot", "Lettre", "Chinois", "Francais", "blablabla", "franchement c'est ca", "https://www.willisesfsefsefam.fr", null, 10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.fr", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.fr5", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.fr2", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.fr01", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.f1r", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.f0r", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.f0r", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.williaghm.fr", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.wilsglrgiam.fr", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.wisglliam.fr", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.wsgsgilliam.fr", null ,10));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://wwwgssgsg.william.fr", null ,10));
        }
    }*/

    fun loadPartialWords(s: String) {
        thread {
            resultPartialWord.postValue(dao.loadPartialWords(s))
        }
    }

    fun deleteWord(url: String) {
        thread {
            val word = dao.getWordByKey(url)
            if (word != null) {
                dao.deleteWord(word)
            }
        }
    }

    fun updateWord(word: Words) {
        thread {
             dao.updateWord(word)
        }
    }

}