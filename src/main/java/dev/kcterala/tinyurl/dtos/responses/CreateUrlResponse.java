package dev.kcterala.tinyurl.dtos.responses;

public record CreateUrlResponse(
    String longUrl,
    String shortUrl
) {
}
