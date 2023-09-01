package com.mate.carHunter.components.models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.time.Instant;

public class Image {
    private BigInteger marketplaceId;
    private String imageContent;

    public BigInteger getMarketplaceId() {
        return marketplaceId;
    }
    public void setMarketplaceId(BigInteger marketplaceId) {
        this.marketplaceId = marketplaceId;
    }
    public String getImageContent() {
        return imageContent;
    }
    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
}
