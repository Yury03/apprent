package com.example.apprent.domain.usecase.get_links;

import java.util.List;

public interface LinksCallback {
    void onLinksLoaded(List<String> links);
}
