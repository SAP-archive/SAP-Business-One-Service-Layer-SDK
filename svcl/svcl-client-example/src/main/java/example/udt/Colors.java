package example.udt;

import feign.Param;
import feign.RequestLine;

public interface Colors 
{
	@RequestLine("GET /Color('code')")
	Color get(@Param(value="code") String color);
}
