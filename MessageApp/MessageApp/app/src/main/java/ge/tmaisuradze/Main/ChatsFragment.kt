package ge.tmaisuradze.Main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ge.tmaisuradze.Entities.Chat
import ge.tmaisuradze.R

class ChatsFragment() : Fragment() {

    var presenter: IMainPresenter? = null
    private lateinit var search: EditText
    private lateinit var scrollView: NestedScrollView
    private lateinit var rv: RecyclerView
    private var listener: ChatsLoadedListener? = null
    private lateinit var fragment: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        fragment = view
        scrollView = view.findViewById(R.id.main_scroll_view)
        rv = view.findViewById(R.id.conversations_rv)
        rv.adapter = ChatsAdapter(emptyList(), presenter)
        search = view.findViewById(R.id.main_search_bar)
        listener?.onFragmentLoaded()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.listener = context as ChatsLoadedListener
    }

    fun setInfo(chats: List<Chat>, index: Int) {
        (rv.adapter as ChatsAdapter).updateData(chats, index)
    }

    fun getSearch(): EditText {
        if(!::search.isInitialized){
            search = fragment.findViewById(R.id.main_scroll_view)
        }
        return search
    }

    fun getScrollView(): NestedScrollView {
        if(!::scrollView.isInitialized){
            scrollView = fragment.findViewById(R.id.main_scroll_view)
        }
        return scrollView
    }

}

interface ChatsLoadedListener {
    fun onFragmentLoaded()
}