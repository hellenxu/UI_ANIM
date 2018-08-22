package com.six.ui.data

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-12.
 */
data class StoreDetails(val formatted_address: String,
                        val website: String,
                        val opening_hours: OpeningHours,
                        val geometry: GeoInfo,
                        val plus_code: PlusCode)