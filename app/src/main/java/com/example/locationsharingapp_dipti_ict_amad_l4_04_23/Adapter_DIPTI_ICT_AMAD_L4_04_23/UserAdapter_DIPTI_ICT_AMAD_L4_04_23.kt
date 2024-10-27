package com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Adapter_DIPTI_ICT_AMAD_L4_04_23

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Model_DIPTI_ICT_AMAD_L4_04_23.User_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.databinding.ItemUserDiptiIctAmadL40423Binding


class UserAdapter_DIPTI_ICT_AMAD_L4_04_23(private var userList: List<User_DIPTI_ICT_AMAD_L4_04_23>): RecyclerView.Adapter<UserAdapter_DIPTI_ICT_AMAD_L4_04_23.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUserDiptiIctAmadL40423Binding):RecyclerView.ViewHolder(binding.root){
        fun bind(user: User_DIPTI_ICT_AMAD_L4_04_23){
            binding.apply {
                binding.displayNameTxt.text = user.displayName
                binding.emailTxt.text = user.email
                binding.locationTxt.text = user.location
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserDiptiIctAmadL40423Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.bind(user)

    }
    fun updateData(newList: List<User_DIPTI_ICT_AMAD_L4_04_23>) {
        userList = newList
        notifyDataSetChanged()
    }
}