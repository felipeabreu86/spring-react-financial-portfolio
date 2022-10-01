package com.financialportfolio.backend.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.financialportfolio.backend.mock.TokenMock;

class JsonUtilTest {


    private static final String jsonValido1 = "{\"access_token\":\"123456\",\"token_type\":\"Bearer\"}";
    private static final String jsonValido2 = "{\"access_token\":\"123456\",\"token_type\": null}";
    private static final String jsonValido3 = "{\"access_token\":\"123456\",\"token_type\": \"\"}";

    private static final String jsonInvalido1 = "{'access_token':'123456','token_type':'Bearer'}";
    private static final String jsonInvalido2 = "{'access_token':\"123456\",'token_type':'Bearer'}";
    private static final String jsonInvalido3 = "{'access_token':\"123456\",'token_type':'Bearer'}";
    private static final String jsonInvalido4 = "{\"access_token\":\"123456\",\"token_type\":\"Bearer\"";

    @Test
    public void testarJsonValido() {
        assertTrue(JsonUtil.isValidJson("{}"));
        assertTrue(JsonUtil.isValidJson("[]"));
        assertTrue(JsonUtil.isValidJson("[{}]"));
        assertTrue(JsonUtil.isValidJson("[{},{}]"));
        assertTrue(JsonUtil.isValidJson(jsonValido1));
        assertTrue(JsonUtil.isValidJson(jsonValido2));
        assertTrue(JsonUtil.isValidJson(jsonValido3));
    }

    @Test
    public void testarJsonInvalido() {
        assertFalse(JsonUtil.isValidJson(null));
        assertFalse(JsonUtil.isValidJson(""));
        assertFalse(JsonUtil.isValidJson(" "));
        assertFalse(JsonUtil.isValidJson(jsonInvalido1));
        assertFalse(JsonUtil.isValidJson(jsonInvalido2));
        assertFalse(JsonUtil.isValidJson(jsonInvalido3));
        assertFalse(JsonUtil.isValidJson(jsonInvalido4));
    }

    @Test
    public void testarJsonArrayValido() {
        assertTrue(JsonUtil.isJsonArray("[]"));
        assertTrue(JsonUtil.isJsonArray("[{}]"));
        assertTrue(JsonUtil.isJsonArray("[{},{}]"));
        assertTrue(JsonUtil.isJsonArray("[{\"key\":\"value\"}]"));
        assertTrue(JsonUtil.isJsonArray("[{\"key\":\"value\"}, {\"key\":\"value\"}]"));
        assertTrue(JsonUtil.isJsonArray("[{\"key\":[{\"key\":\"value\"}]}]"));
    }

    @Test
    public void testarJsonArrayInvalido() {
        assertFalse(JsonUtil.isJsonArray(null));
        assertFalse(JsonUtil.isJsonArray(""));
        assertFalse(JsonUtil.isJsonArray(" "));
        assertFalse(JsonUtil.isJsonArray("{}"));
        assertFalse(JsonUtil.isJsonArray(jsonValido1));
        assertFalse(JsonUtil.isJsonArray(jsonValido2));
        assertFalse(JsonUtil.isJsonArray(jsonValido3));
        assertFalse(JsonUtil.isJsonArray("[\"key\":\"value\"]"));
        assertFalse(JsonUtil.isJsonArray("{[{\"key\":\"value\"}]}"));
    }

    @Test
    public void testarConverterObjetoParaJson() {
        TokenMock tokenMock = new TokenMock("123456", "Bearer");
        Optional<String> json = JsonUtil.toJson(tokenMock);
        if (json.isPresent()) {
            String jsonText = json.get();
            assertTrue(jsonText.contains("123456"));
            assertTrue(jsonText.contains("Bearer"));
            assertTrue(JsonUtil.isValidJson(jsonText));
        } else {
            fail();
        }

        json = JsonUtil.toJson(jsonValido1);
        if (json.isPresent()) {
            String jsonText = json.get();
            assertTrue(jsonText.contains("access_token"));
            assertTrue(JsonUtil.isValidJson(jsonText));
        } else {
            fail();
        }

        json = JsonUtil.toJson("teste");
        if (json.isPresent()) {
            String jsonText = json.get();
            assertTrue(jsonText.contains("teste"));
            assertTrue(JsonUtil.isValidJson(jsonText));
        } else {
            fail();
        }

        json = JsonUtil.toJson(null);
        if (json.isPresent()) {
            fail();
        }
    }

    @Test
    public void testarConverterJsonParaObjetoComSucesso() {
        Optional<TokenMock> optional = JsonUtil.readValue(jsonValido1, TokenMock.class);
        if (!optional.isPresent()) {
            fail();
        }
        TokenMock tokenMock = optional.get();
        assertNotNull(tokenMock);
        assertEquals("123456", tokenMock.getAccess_token());
        assertEquals("Bearer", tokenMock.getToken_type());
    }

    @Test
    public void testarConverterJsonParaObjetoComFalha() {
        Optional<TokenMock> optional = JsonUtil.readValue(jsonInvalido1, TokenMock.class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValue(null, TokenMock.class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValue("", TokenMock.class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValue(" ", TokenMock.class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValue("{\"key\":\"value\"}", TokenMock.class);
        if (optional.isPresent()) {
            fail();
        }
    }

    @Test
    public void testarConverterJsonParaListaDeObjetoComSucesso() {
        String jsonList = "[{\"access_token\":\"123456\",\"token_type\":\"Bearer\"}, {\"access_token\":\"654321\",\"token_type\":\"Bearer\"}]";

        Optional<TokenMock[]> optional = JsonUtil.readValues(jsonList, TokenMock[].class);
        if (!optional.isPresent()) {
            fail();
        }
        TokenMock[] tokenMockItems = optional.get();

        assertNotNull(tokenMockItems);
        assertEquals(2, tokenMockItems.length);
        assertEquals("123456", tokenMockItems[0].getAccess_token());
        assertEquals("654321", tokenMockItems[1].getAccess_token());
    }

    @Test
    public void testarConverterJsonParaListaDeObjetoComFalha() {
        Optional<TokenMock[]> optional = JsonUtil.readValues(jsonInvalido1, TokenMock[].class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValues(null, TokenMock[].class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValues("", TokenMock[].class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValues(" ", TokenMock[].class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValues("{\"key\":\"value\"}", TokenMock[].class);
        if (optional.isPresent()) {
            fail();
        }
        optional = JsonUtil.readValues("[{\"key\":\"value\"}]", TokenMock[].class);
        if (optional.isPresent()) {
            fail();
        }
    }

    @Test
    public void testarLerValorDeUmaChaveEspecifica() {
        Optional<String> optional = JsonUtil.readValueByKey(jsonValido1, "token_type");
        if (!optional.isPresent()) {
            fail();
        }
        assertEquals("Bearer", optional.get());

        optional = JsonUtil.readValueByKey(jsonInvalido1, "token_type");
        if (optional.isPresent()) {
            fail();
        }

        optional = JsonUtil.readValueByKey(jsonInvalido1, null);
        if (optional.isPresent()) {
            fail();
        }

        optional = JsonUtil.readValueByKey(null, null);
        if (optional.isPresent()) {
            fail();
        }

        optional = JsonUtil.readValueByKey(jsonValido1, "token_type2");
        if (optional.isPresent()) {
            fail();
        }
    }

}
