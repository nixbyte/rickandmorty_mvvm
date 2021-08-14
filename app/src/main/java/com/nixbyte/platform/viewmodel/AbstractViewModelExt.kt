package com.nixbyte.platform.viewmodel

import com.nixbyte.rickandmortymvvm.common.PaginatedRecyclerView


fun AbstractViewModel.preparePaginationRequestParameter(offsetAndSize: PaginatedRecyclerView.OffsetAndSize) : String {
    var ids = ""
    for (i in offsetAndSize.offset until offsetAndSize.offset+offsetAndSize.pageSize) {
        ids += ",${i+1}"
    }
    return ids.trimStart {
        it == ','
    }
}