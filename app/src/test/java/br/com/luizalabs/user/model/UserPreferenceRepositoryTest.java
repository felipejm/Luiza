package br.com.luizalabs.user.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserPreferenceRepositoryTest {

    @Mock
    SharedPreferences.Editor editor;

    @Mock
    private SharedPreferences preferences;

    @Mock
    private Context appContext;

    private Gson gson = new Gson();
    private UserPreferenceRepository repository;

    private String userPreferenceJsonMock = "{\"temperaturaUnit\":\"CELSIUS\",\"lastLocationLatitude\":-8.05428,\"lastLocationLongitude\":-34.8813}";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new UserPreferencePreferenceRepositoryImpl(preferences, gson);

        Mockito.doReturn(editor).when(preferences).edit();
    }

    @Test
    public void save(){
        UserPreference userPreference = UserPreferenceTestUtil.create();
        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor);

        repository.save(userPreference);
        Mockito.verify(editor).putString(Mockito.eq("userPreferences"),Mockito.eq(userPreferenceJsonMock));
    }

    @Test
    public void get(){
        UserPreference userPreference = UserPreferenceTestUtil.create();
        Mockito.when(preferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn(userPreferenceJsonMock);

        UserPreference userPreferenceResult = repository.get();
        Assert.assertEquals(userPreference, userPreferenceResult);
    }

    @Test
    public void get_empty(){
        UserPreference userPreference = new UserPreference();
        Mockito.when(preferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("{}");

        UserPreference userPreferenceResult = repository.get();
        Assert.assertEquals(userPreference, userPreferenceResult);
    }
}