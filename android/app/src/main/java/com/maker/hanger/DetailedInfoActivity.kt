package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.connection.DetailedInfoView
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ActivityDetailedInfoBinding

class DetailedInfoActivity : AppCompatActivity(), DetailedInfoView {
    private lateinit var binding: ActivityDetailedInfoBinding
    private lateinit var clothes: Clothes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        closeDetailedInfo()
    }

    override fun onStart() {
        super.onStart()

        initClothesInfo()
        searchInfoClothes()
    }

    private fun searchInfoClothes() {
        val clothesIdx = intent.getStringExtra("clothesIdx")!!.toInt()
        val clothesService = ClothesService()
        clothesService.setDetailedInfoView(this)
        clothesService.searchInfo(getJwt(), clothesIdx)
    }

    private fun searchWashingMethod() {
        val intent = Intent(this, WashingMethodActivity::class.java)
        with (binding) {
            detailInfoClothesWashing40Iv.setOnClickListener {
                intent.putExtra("washing", 40)
                startActivity(intent)
            }
            detailInfoClothesWashing60Iv.setOnClickListener {
                intent.putExtra("washing", 60)
                startActivity(intent)
            }
            detailInfoClothesWashing95Iv.setOnClickListener {
                intent.putExtra("washing", 95)
                startActivity(intent)
            }
            detailInfoClothesWashingNoIv.setOnClickListener {
                intent.putExtra("washing", 0)
                startActivity(intent)
            }
        }
    }

    private fun updateClothes() {
        binding.detailInfoClothesModifyIv.setOnClickListener {
            val intent = Intent(this, ModifyClothesActivity::class.java)
            intent.putExtra("clothes", clothes)
            startActivity(intent)
        }
    }

    private fun deleteClothes() {
        binding.detailInfoClothesDeleteIv.setOnClickListener {
            val clothesIdx = intent.getStringExtra("clothesIdx")!!.toInt()
            val clothesService = ClothesService()
            clothesService.setDetailedInfoView(this)
            clothesService.delete(getJwt(), clothesIdx)
        }
    }

    private fun initClothesInfo() {
        initSeason()
        initKind()
        initWashingMethod()
        initSize()
    }

    private fun initSeason() {
        with (binding) {
            detailInfoClothesSpringTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            binding.detailInfoClothesSummerTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesAutumnTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesWinterTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
        }
    }

    private fun initKind() {
        with(binding) {
            detailInfoClothesTopTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesOuterTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesBottomsTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesOnepieceTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesShoesTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesAccessoriesTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
        }
    }

    private fun initWashingMethod() {
        with (binding) {
            detailInfoClothesWashing40Iv.visibility = View.GONE
            detailInfoClothesWashing60Iv.visibility = View.GONE
            detailInfoClothesWashing95Iv.visibility = View.GONE
            detailInfoClothesWashingNoIv.visibility = View.GONE
        }
    }

    private fun initSize() {
        with (binding) {
            detailInfoClothesSmallTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesMediumTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
            detailInfoClothesLargeTv
                .setBackgroundResource(R.drawable.clothes_search_select_off_background)
        }
    }

    private fun setClothesInfo() {
        setBookmark(clothes.bookmark)
        setPhoto()
        setDate()
        setSeason()
        setKind()
        setWashingMethod()
        setSize()
    }

    private fun setBookmark(isLike: Boolean) {
        if (isLike) {
            binding.detailInfoClothesBookmarkIv.setImageResource(R.drawable.bookmark_on_search)
        } else {
            binding.detailInfoClothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)
        }
    }

    private fun setPhoto() {
        Glide.with(applicationContext).load(clothes.clothesImageUrl).override(350, 480)
            .into(binding.detailInfoClothesIv)
    }

    private fun setDate() {
        binding.detailInfoClothesDateInputTv.text = clothes.date
    }

    private fun setSeason() {
        for (season in clothes.season) {
            when (season) {
                "spring" -> binding.detailInfoClothesSpringTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "summer" -> binding.detailInfoClothesSummerTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "autumn" -> binding.detailInfoClothesAutumnTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "winter" -> binding.detailInfoClothesWinterTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            }
        }
    }

    private fun setKind() {
        for (kind in clothes.kind) {
            when (kind) {
                "top" -> binding.detailInfoClothesTopTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "outer" -> binding.detailInfoClothesOuterTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "bottoms" -> binding.detailInfoClothesBottomsTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "onepiece" -> binding.detailInfoClothesOnepieceTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "shoes" -> binding.detailInfoClothesShoesTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "accessories" -> binding.detailInfoClothesAccessoriesTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            }
        }
    }

    private fun setWashingMethod() {
        for (washingMethod in clothes.washingMethod) {
            when (washingMethod) {
                40 -> {
                    binding.detailInfoClothesWashing40Iv.visibility = View.VISIBLE
                }
                60 -> {
                    binding.detailInfoClothesWashing60Iv.visibility = View.VISIBLE
                }
                95 -> {
                    binding.detailInfoClothesWashing95Iv.visibility = View.VISIBLE
                }
                else -> {
                    binding.detailInfoClothesWashingNoIv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setSize() {
        when (clothes.size) {
            'S' -> binding.detailInfoClothesSmallTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            'M' -> binding.detailInfoClothesMediumTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            'L' -> binding.detailInfoClothesLargeTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
        }
    }

    private fun closeDetailedInfo() {
        binding.detailInfoClothesCloseIv.setOnClickListener {
            finish()
        }
    }

    private fun getJwt(): String? {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        return sharedPreferences.getString("jwt", null)
    }

    private fun removeJwt() {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("jwt")
        editor.apply()
    }

    override fun onSearchInfoSuccess(clothes: Clothes) {
        Log.d("SEARCHINFO/SUCCESS", "의류 상세정보 조회를 성공했습니다.")
        this.clothes = clothes
        Log.d("SEARCH/CLOTHES", this.clothes.toString())
        searchWashingMethod()
        setClothesInfo()
        deleteClothes()
        updateClothes()
    }

    override fun onSearchInfoFailure(status: Int) {
        Log.d("SEARCHINFO/FAILURE", "의류 상세정보 조회를 실패했습니다.")
        when (status) {
            400 -> {
                Toast.makeText(this,"의류 상세정보 조회를 실패했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> {
                Toast.makeText(this, "토큰이 유효하지 않습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show()
                removeJwt()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }

    override fun onDeleteSuccess() {
        Log.d("DELETE/SUCCESS", "의류 삭제를 성공했습니다.")
        Toast.makeText(this, "의류를 삭제했습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDeleteFailure(status: Int) {
        Log.d("DELETE/FAILURE", "의류 삭제를 실패했습니다.")
        when (status) {
            400 -> {
                Toast.makeText(this, "의류 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "토큰이 유효하지 않습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show()
                removeJwt()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }
}