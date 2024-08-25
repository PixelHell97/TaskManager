package com.pixelh97.taskmanager.presentation.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.pixelh97.taskmanager.R
import com.pixelh97.taskmanager.data.model.Project
import com.pixelh97.taskmanager.databinding.FragmentHomeBinding
import com.pixelh97.taskmanager.presentation.main.fragments.home.adapter.ProjectCardsAdapter
import kotlin.math.abs

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var projectAdapter: ProjectCardsAdapter
    private val fakeProjectsList: List<Project> =
        listOf(
            Project(1, "Back-End Development", "", "Oct 22, 2020"),
            Project(2, "Front-End Development", "", "Dec 22, 2020"),
            Project(3, "Android Development", "", "Jun 22, 2020"),
            Project(4, "Flutter Development", "", "July 22, 2020"),
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        projectAdapter = ProjectCardsAdapter(fakeProjectsList)

        binding.projectsVp.clipToPadding = false
        binding.projectsVp.setClipChildren(false)
        binding.projectsVp.setOffscreenPageLimit(3)
        binding.projectsVp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)

        val compositePageTransformer =
            CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(pageMarginPx))
                addTransformer { page, position ->
                    val r = 1 - abs(position)
                    val scaleFactor = 0.85f + r * 0.1f
                    page.scaleY = scaleFactor
                }
            }

        binding.projectsVp.setPageTransformer(compositePageTransformer)
        binding.projectsVp.adapter = projectAdapter
        binding.dotsIndicator.attachTo(binding.projectsVp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
