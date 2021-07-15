package com.example.sw_api.service;

import com.example.sw_api.webclient.DataFetcherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataFetcherService {

    private final DataFetcherClient dataFetcherClient;

    public void getData() {
        dataFetcherClient.fetchData();
    }

}
