package com.advancedcomputation.logos_android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.db.Wallet

class WalletAdapter(private var wallets: List<Wallet>) : RecyclerView.Adapter<WalletAdapter.ViewHolder>()
{


    class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val name: TextView =
            view.findViewById(R.id.walletName)

        val currency: TextView =
            view.findViewById(R.id.walletCurrency)

        val balance: TextView =
            view.findViewById(R.id.walletBalance)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.wallet_item,
                    parent,
                    false
                )

        return ViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val wallet = wallets[position]
        holder.name.text = wallet.name
        holder.currency.text = wallet.currency
        holder.balance.text = "${wallet.balance} ${wallet.currency}"
    }


    override fun getItemCount(): Int = wallets.size


    fun updateWallets(newWallets: List<Wallet>)
    {
        wallets = newWallets
        notifyDataSetChanged()
    }
}