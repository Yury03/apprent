package com.example.apprent.domain.usecase;

import java.util.List;

public interface LinksCallback {
    void onLinksLoaded(List<String> links);
}
