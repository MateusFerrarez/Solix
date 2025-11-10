package br.lumago.solix.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.lumago.solix.data.entities.relations.CustomerCard
import kotlinx.coroutines.delay

class DelayedCustomersPagingSource(
    private val delegate: PagingSource<Int, CustomerCard>,
    private val delayMs: Long = 500L
) : PagingSource<Int, CustomerCard>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CustomerCard> {
        if (delayMs > 0) delay(delayMs)
        return delegate.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int, CustomerCard>): Int? {
        return delegate.getRefreshKey(state)
    }
}