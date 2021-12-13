package joaomenna1.com.github.pageheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import joaomenna1.com.github.pageheroes.MainActivity
import joaomenna1.com.github.pageheroes.R
import joaomenna1.com.github.pageheroes.data.Character
import joaomenna1.com.github.pageheroes.data.Characters
import joaomenna1.com.github.pageheroes.databinding.FragmentHomeBinding
import joaomenna1.com.github.pageheroes.util.LoadController
import joaomenna1.com.github.pageheroes.util.Tracker
import joaomenna1.com.github.pageheroes.view.adapter.CharacterAdapter
import joaomenna1.com.github.pageheroes.viewmodel.HomeViewModel
import com.microsoft.appcenter.crashes.Crashes
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter
    private val itemsCharacters: MutableList<Character> = mutableListOf()
    private var section = 1

    private lateinit var loadController: LoadController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater).let {
        it.lifecycleOwner = viewLifecycleOwner
        it.viewModel = viewModel
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setComponents()
    }

    private fun setComponents() {
        setRecycler()
        setListeners()

        setLoad()
        configureObservers()
        viewModel.getList()
    }

    private fun setLoad() {
        loadController = LoadController(requireContext())
        loadController.showStandardLoading(getString(R.string.loading_characters))
    }

    private fun configureObservers() {
        viewModel.let {
            it.getCharactersLiveData.observe(
                viewLifecycleOwner,
                ::onGetCharacters
            )

            it.errorGetCharsLiveData.observe(
                viewLifecycleOwner,
                ::onErrorGetCharacters
            )
        }
    }

    private fun onGetCharacters(chars: Characters) {

        Tracker.track(
            Tracker.TrackType.DONE,
            Tracker.EventType.LOAD_HERO,
            "Section: $section\nItems: ${chars.data.results.size}"
        )

        itemsCharacters.apply {
            clear()
            addAll(chars.data.results)
        }

        if (itemsCharacters.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.chars_not_found),
                LENGTH_LONG
            ).show()
            next_arrow.isEnabled = false
        }

        loadController.dismissIt()
        adapter.notifyDataSetChanged()
    }

    private fun onErrorGetCharacters(error: Throwable) {
        error.printStackTrace()
        loadController.dismissIt()
        Crashes.trackError(error)
        Tracker.track(
            Tracker.TrackType.FAILED,
            Tracker.EventType.LOAD_HERO,
            "Section: $section"
        )
    }

    private fun setRecycler() {
        val viewManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = CharacterAdapter(recycler_items, itemsCharacters) { character, position ->
            val charDetailsDialog = CharDetailsDialog(character)

            Tracker.track(
                Tracker.TrackType.DONE,
                Tracker.EventType.SELECT_HERO,
                "Char selected: ${character.id} - ${character.name}"
            )

            charDetailsDialog.show(childFragmentManager, CharDetailsDialog.TAG)
        }

        adapter.setHasStableIds(true)

        recycler_items.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = this@HomeFragment.adapter
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.select_end,
            R.id.select_start,
            R.id.select_middle -> {
                update(v as AppCompatButton)
            }
            R.id.next_arrow,
            R.id.previous_arrow -> {
                setPageBtts(v)
                next_arrow.isEnabled = true
            }
        }
        input_search_char.setText("")
        MainActivity.hideKeyboard(activity as MainActivity)
    }

    private fun deselectOthers(v: View) {

        val items = layout_page_btns.childCount
        var count = 0

        while (count < items) {
            val view = layout_page_btns.getChildAt(count)
            view.isSelected = v == view
            count++
        }
    }

    private fun setPageBtts(v: View) {
        if (v.id == R.id.next_arrow) {
            select_start.add(3)
            select_middle.add(3)
            select_end.add(3)

            section++
        } else {
            if (section > 1) {
                select_start.redux(3)
                select_middle.redux(3)
                select_end.redux(3)
                section--
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.already_in_first_page),
                    LENGTH_LONG
                ).show()
            }
        }
        update(select_start)
    }

    private fun setListeners() {
        select_start.setOnClickListener(this)
        select_middle.setOnClickListener(this)
        select_end.setOnClickListener(this)
        previous_arrow.setOnClickListener(this)
        next_arrow.setOnClickListener(this)

        input_search_char.setOnEditorActionListener() OnEditorActionListener@{ v, actionId, event ->
            if (actionId == IME_ACTION_SEARCH) {
                MainActivity.hideKeyboard(activity as MainActivity)
                viewModel.getList(nameStartsWith = input_search_char.text.toString())
                input_search_char.isFocusable = false
                input_search_char.isFocusableInTouchMode = true

                loadController.showStandardLoading(getString(R.string.searching_characters))

                Tracker.track(
                    Tracker.TrackType.DONE,
                    Tracker.EventType.SEARCH_HERO,
                    "Char: ${input_search_char.text}"
                )

                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        }
    }

    private fun update(v: AppCompatButton) {
        deselectOthers(v)
        val page = v.text.toString().toInt()

        loadController.showStandardLoading(getString(R.string.loading_characters))
        viewModel.getList(page = page)
    }

    private fun TextView.add(value: Int) {
        val textToInt = this.text.toString().toInt()
        this.text = (textToInt + value).toString()
    }

    private fun TextView.redux(value: Int) {
        val textToInt = this.text.toString().toInt()
        this.text = (textToInt - value).toString()
    }
}