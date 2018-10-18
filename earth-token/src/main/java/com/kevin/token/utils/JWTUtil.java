package com.kevin.token.utils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JWTUtil {

	public static final long EXPIRATION_TIME = 3600_000_000L; // 1000 hour 过期时间
	static final String SECRET = "ThisIsASecret"; // 加密 secret ，一般不对外公布，写死在配置文件或者配置类中
	static final String TOKEN_PREFIX = "Bearer"; // token 的前缀
	static final String HEADER_STRING = "Authorization"; // 头部请求的 key 值

	/**
	 * 生成 token 的方法，可以自定义放入 token 的信息
	 * 
	 * @param username
	 * @param generateTime
	 * @return
	 */
	public static String generateToken(String username, Date generateTime) {
		Map<String, Object> map = new HashMap<>();
		// 可以把任何安全的数据放到map里面
		map.put("username", username);
		String jwt = Jwts.builder().setClaims(map).setExpiration(new Date(generateTime.getTime() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		System.out.println("jwt = " + jwt);
		return jwt;
	}

	/**
	 * @param token
	 * @return
	 */
	public static Map<String, Object> validateToken(String token) {
		Map<String, Object> resp = new HashMap<String, Object>();
		if (token != null) {
			// 解析token
			try {
				Map<String, Object> body = Jwts.parser().setSigningKey(SECRET)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
				String username = (String) (body.get("username"));
				printMap(body);
				if (username == null || username.isEmpty()) {
					resp.put("ERR_MSG", "用户名为空");
					return resp;
				}
				// 账号在别处登录
				// if(userService.findByUsername(username).getLastLoginTime().after(generateTime)){
				// resp.put("ERR_MSG",Constants.ERR_MSG_LOGIN_DOU);
				// return resp;
				// }
				resp.put("username", username);
				return resp;
			} catch (SignatureException | MalformedJwtException e) {
				// TODO: handle exception
				// don't trust the JWT!
				// jwt 解析错误
				resp.put("ERR_MSG", "token 解析错误，该请求不适合使用 jwt 解析");
				return resp;
			} catch (ExpiredJwtException e) {
				// TODO: handle exception
				// jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
				resp.put("ERR_MSG", "token 已过期");
				return resp;
			}
		} else {
			resp.put("ERR_MSG", "token 是空");
			return resp;
		}
	}

	private static void printMap(Map<String, Object> body) {
		Iterator<String> it = body.keySet().iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			System.out.println("key = " + key + "; value = " + body.get(key));
		}
	}
}
