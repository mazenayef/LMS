package org.lms.authentication.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.lms.user.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
	public static final String SECRET = "wussupitssomelongtextjustfortheerrortogoawayasitneedsasecretkeylongenough";

	public String generateToken(Integer id, User.Role role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", id);
		claims.put("role", role);

		return createToken(claims, id);
	}

	private String createToken(Map<String, Object> claims, Integer id) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(String.valueOf(id))
				.setIssuedAt(new Date())
				.setExpiration(null)
				.signWith(getSignKey())
				.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
