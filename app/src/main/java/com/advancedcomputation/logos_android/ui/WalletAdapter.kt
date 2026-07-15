package com.advancedcomputation.logos_android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.advancedcomputation.logos_android.LogosApplication
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.db.Wallet

class WalletAdapter(private var wallets: List<Wallet>,
                    private val onWalletClicked: (Wallet) -> Unit,
                    private val onWalletMenuClicked: (Wallet) -> Unit) :
    RecyclerView.Adapter<WalletAdapter.ViewHolder>()
{

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val name: TextView = view.findViewById(R.id.walletName)
        val currency: TextView = view.findViewById(R.id.walletCurrency)
        val balance: TextView = view.findViewById(R.id.walletBalance)
        val menu: ImageButton = view.findViewById(R.id.wallet_menu)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallet_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val wallet = wallets[position]
        holder.name.text = wallet.name
        holder.currency.text = wallet.currency
        holder.balance.text = "${wallet.balance} ${wallet.currency}"
        holder.menu.setOnClickListener {
            onWalletMenuClicked(wallet)
        }

        holder.itemView.setOnClickListener {
            onWalletClicked(wallet)
        }
    }


    override fun getItemCount(): Int = wallets.size

    fun updateWallets(newWallets: List<Wallet>)
    {
        wallets = newWallets
        notifyDataSetChanged()
    }
}