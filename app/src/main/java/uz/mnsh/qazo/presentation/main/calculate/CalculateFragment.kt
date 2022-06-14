package uz.mnsh.qazo.presentation.main.calculate

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import uz.mnsh.qazo.R
import uz.mnsh.qazo.databinding.FragmentCalculateBinding
import uz.mnsh.qazo.databinding.ItemDialogPrayerBinding
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.presentation.main.MainActivity
import uz.mnsh.qazo.presentation.main.adapter.PrayerAdapter
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CalculateFragment : Fragment(), PrayerAdapter.OnPrayerClickListener {

    private var _binding: FragmentCalculateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculateViewModel by viewModels()
    private lateinit var adapter: PrayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    @SuppressLint("LogNotTimber")
    private fun observeData() {
        adapter = PrayerAdapter(this)
        viewModel.getPrayers().observe(viewLifecycleOwner) { mlist ->
            mlist.let { prayers ->
                val list = prayers.sortedBy { it.range }
                adapter.submitList(list)
                setUpCard(list)
            }
        }
        binding.prayerRv.adapter = adapter
    }

    @SuppressLint("SetTextI18n", "LogNotTimber")
    private fun setUpCard(list: List<Prayer>) {
        var isOld = false
        val today = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(Date())

        binding.apply {
            var todayCount = 0
            var allPerformed = 0
            var allRemained = 0

            list.forEach { prayer ->
                if (prayer.date == today) {
                    todayCount += prayer.todayPerformedCount
                } else {
                    isOld = true
                }
                allPerformed += prayer.performedCount
                allRemained += prayer.remainingCount
            }

            allCountTv.text = "$allRemained қазо, $allPerformed ўқилди"
            todayCountTv.text = todayCount.toString()

            progressBar.max = allRemained + allPerformed
            progressBar.progress = allPerformed

            percentTv.text = ((allPerformed * 100) / allRemained).toString()

            prayerCard.setOnClickListener {
                Navigation.findNavController(
                    activity as MainActivity,
                    R.id.nav_host_fragment_content_main
                )
                    .navigate(R.id.nav_edit)
            }

            if (isOld) {
                viewModel.updateDate(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onPrayerClicked(prayer: Prayer, position:Int) {
        val dialog = AlertDialog.Builder(binding.root.context).create()
        val dialogBinding =
            ItemDialogPrayerBinding.inflate(LayoutInflater.from(binding.root.context))
        dialog.setView(dialogBinding.root)
        dialogBinding.apply {
            titleTv.text =
                "${prayer.prayerTimeName} намози билан боғлиқ ўзингизга мос ҳолатни танланг:"
            accidentBtn.setOnClickListener {
                prayer.remainingCount += 1
                viewModel.updatePrayer(prayer)
                adapter.notifyItemChanged(position)
                dialog.dismiss()
            }
            if (prayer.remainingCount > 0) {
                performedBtn.setOnClickListener {
                    prayer.todayPerformedCount += 1
                    prayer.remainingCount += -1
                    prayer.performedCount = if (prayer.remainingCount == 0)  0
                    else prayer.performedCount + 1
                    viewModel.updatePrayer(prayer)
                    adapter.notifyItemChanged(position)
                    dialog.dismiss()
                }
            } else {
                performedBtn.visibility = View.GONE
            }
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}