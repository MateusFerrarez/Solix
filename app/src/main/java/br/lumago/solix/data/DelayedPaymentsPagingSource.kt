package br.lumago.solix.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.lumago.solix.data.entities.relations.PaymentCard
import kotlinx.coroutines.delay

class DelayedPaymentsPagingSource(
    private val delegate: PagingSource<Int, PaymentCard>,
    private val delayMs: Long = 500L
) : PagingSource<Int, PaymentCard>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PaymentCard> {
        if (delayMs > 0) delay(delayMs)
        return delegate.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int, PaymentCard>): Int? {
        return delegate.getRefreshKey(state)
    }
}