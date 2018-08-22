package com.six.ui.data

import java.util.*

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-12.
 */
data class NearbyResponse(val status: String, val results: Array<TargetInfo>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NearbyResponse

        if (status != other.status) return false
        if (!Arrays.equals(results, other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + Arrays.hashCode(results)
        return result
    }
}