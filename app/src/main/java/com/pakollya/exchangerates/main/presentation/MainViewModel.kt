package com.pakollya.exchangerates.main.presentation

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.presentation.BottomNavigationCommunication
import com.pakollya.exchangerates.base.presentation.ViewModelAbstract
import com.pakollya.exchangerates.base.presentation.Visibility
import javax.inject.Inject

class MainViewModel @Inject constructor(
    communication: BottomNavigationCommunication,
    dispatchers: Dispatchers
) : ViewModelAbstract<Visibility>(
    communication,
    dispatchers
)