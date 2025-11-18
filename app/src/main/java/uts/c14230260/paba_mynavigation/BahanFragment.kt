package uts.c14230260.paba_mynavigation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BahanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BahanFragment : Fragment() {

    // Baru
    private var _namaBahan : MutableList<String> = emptyList<String>().toMutableList()
    private var _kategoriBahan : MutableList<String> = emptyList<String>().toMutableList()
    private var _linkGambar : MutableList<String> = emptyList<String>().toMutableList()
    lateinit var sp : SharedPreferences

    private var arBahan = arrayListOf<dcBahan>()
    private lateinit var _rvBahan : RecyclerView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    // Latihan pert 13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bahan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Baru
        sp = requireContext().getSharedPreferences("SP_Bahan", MODE_PRIVATE)

        val gson = Gson()
        val isiSP = sp.getString("spBahan", null)
        val type = object : TypeToken<List<dcBahan>>() {}.type
        if (isiSP != null) {
            arBahan = gson.fromJson(isiSP, type)
        }

        _rvBahan = view.findViewById(R.id.rvBahan)
        if (arBahan.size == 0) {
            SiapkanData()
        } else {
            arBahan.forEach {
                _namaBahan.add(it.nama)
                _kategoriBahan.add(it.kategori)
                _linkGambar.add(it.linkGambar)
            }
            arBahan.clear()
        }
        TambahData()
        TampilkanData()

        // End baru

//        // Latihan pert 13 (ListView)
//        var data = mutableListOf<String>()
//        data.addAll(listOf(
//            "1 Nama bahan: Wortel, Kategori: Sayur",
//            "2 Nama bahan: Ayam, Kategori: Daging",
//            "3 Nama bahan: Bawang, Kategori: Bumbu"
//        ))
//
//        val lvAdapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_list_item_1,
//            data
//        )
//        val _lv1 = view.findViewById<ListView>(R.id.rvBahan)
//        _lv1.adapter = lvAdapter
//
//        val _btnTambah = view.findViewById<Button>(R.id.btnTambahData)
//        _btnTambah.setOnClickListener {
//            val lastItem = data.getOrNull(data.size - 1) ?: "0"
//            val lastNumberStr = lastItem.split(" ").firstOrNull() ?: "0"
//            val lastNumber = try {
//                Integer.parseInt(lastNumberStr)
//            } catch (e: NumberFormatException) { 0 }
//            val dtAkhir = lastNumber + 1
//            val etNamaBahan = view.findViewById<EditText>(R.id.etNamaBahan)
//            val etKategori = view.findViewById<EditText>(R.id.etKategori)
//            if (etNamaBahan.text.toString().trim().isEmpty() || etKategori.text.toString().trim().isEmpty()) {
//                Toast.makeText(
//                    requireContext(),
//                    "Data baru tidak boleh kosong",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                data.add("$dtAkhir Nama bahan: ${etNamaBahan.text}, Kategori: ${etKategori.text}")
//                lvAdapter.notifyDataSetChanged() // update/refresh data
//            }
//        }
//
//        _lv1.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(
//                requireContext(),
//                data[position],
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//        val gestureDetector = GestureDetector(
//            requireContext(),
//            object : GestureDetector.SimpleOnGestureListener() {
//                override fun onDoubleTap(e: MotionEvent): Boolean {
//                    val position = _lv1.pointToPosition(e.x.toInt(), e.y.toInt())
//                    if (position != ListView.INVALID_POSITION) {
//                        val selectedItem = data[position]
//                        showActionDialog(position, selectedItem, data, lvAdapter)
//                    }
//                    return true
//                }
//            }
//        )
//
//        // asumsi
//        _lv1.setOnTouchListener { _, event ->
//            gestureDetector.onTouchEvent(event)
//        }
    }

    fun SiapkanData() {
        _namaBahan = resources.getStringArray(R.array.namaBahan).toMutableList()
        _kategoriBahan = resources.getStringArray(R.array.kategoriBahan).toMutableList()
        _linkGambar = resources.getStringArray(R.array.gambarBahan).toMutableList()
    }

    fun TambahData() {
        val gson = Gson()
        sp.edit {
            arBahan.clear()

            for (position in _namaBahan.indices) {
                val data = dcBahan(
                    _namaBahan[position],
                    _kategoriBahan[position],
                    _linkGambar[position]
                )
                arBahan.add(data)
            }

            val json = gson.toJson(arBahan)
            putString("spBahan", json)
        }
    }

    fun TampilkanData() {
        _rvBahan.layoutManager = LinearLayoutManager(requireContext())
        val adapterWayang = adapterRecView(arBahan)
        _rvBahan.adapter = adapterWayang
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BahanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BahanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun showActionDialog(
        position: Int,
        selectedItem: String,
        data: MutableList<String>,
        adapter: ArrayAdapter<String>
    ) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("ITEM $selectedItem")
        builder.setMessage("Pilih tindakan yang ingin dilakukan:")

        builder.setPositiveButton("Update") {_, _ ->
            showUpdateDialog(position, selectedItem, data, adapter)
        }
        builder.setNegativeButton("Hapus") {_, _ ->
            data.removeAt(position)
            adapter.notifyDataSetChanged() // refresh data
            Toast.makeText(
                requireContext(),
                "Hapus Item $selectedItem",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNeutralButton("Batal") {dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }


    private fun showUpdateDialog(
        position: Int,
        oldValue: String,
        data: MutableList<String>,
        adapter: ArrayAdapter<String>
    ) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update Data")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50,40,50,10)

        val tvOld = TextView(requireContext())
        tvOld.text = "Data lama: $oldValue"
        tvOld.textSize = 16f

        val etNewNamaBahan = EditText(requireContext())
        etNewNamaBahan.hint = "Masukkan nama bahan baru"

        val etNewKategori = EditText(requireContext())
        etNewKategori.hint = "Masukkan kategori baru"

        layout.addView(tvOld)
        layout.addView(etNewNamaBahan)
        layout.addView(etNewKategori)

        builder.setView(layout)

        builder.setPositiveButton("Simpan") { dialog, _ ->
            val lastItem = data.getOrNull(data.size - 1) ?: "0"
            val lastNumberStr = lastItem.split(" ").firstOrNull() ?: "0"
            val lastNumber = try {
                Integer.parseInt(lastNumberStr)
            } catch (e: NumberFormatException) { 0 }
            val dtAkhir = lastNumber + 1
            val newValue = "$dtAkhir Nama bahan: ${etNewNamaBahan.text.toString().trim()}, Kategori: ${etNewKategori.text.toString().trim()}"

            if (newValue.isNotEmpty() || etNewNamaBahan.text.toString().trim().isNotEmpty() || etNewKategori.text.toString().trim().isNotEmpty()) {
                data[position] = newValue
                adapter.notifyDataSetChanged()
                Toast.makeText(
                    requireContext(),
                    "Data diupdate jadi: $newValue",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Data baru tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}