package br.com.luizalabs.user.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.luizalabs.weather.model.TemperatureUnitEnum;

public class UserPreferenceInteractorTest {

    @Mock
    private UserPreferenceRepository repository;

    private UserPreferenceInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new UserPreferencePreferenceInteractorImpl(repository);
    }

    @Test
    public void saveLastLocation(){
        UserPreference userPreferenceFromRepository = UserPreferenceTestUtil.create(-1.0, -2.0);
        Mockito.doReturn(userPreferenceFromRepository).when(repository).get();

        UserPreference userPreference = UserPreferenceTestUtil.create(-3.0, -4.0);
        interactor.saveLastLocation(userPreference.getLastLocationLatitude(), userPreference.getLastLocationLongitude());
        Mockito.verify(repository).save(Mockito.eq(userPreference));
    }

    @Test
    public void saveTemperatureUnit(){
        UserPreference userPreferenceFromRepository = UserPreferenceTestUtil.create(TemperatureUnitEnum.CELSIUS);
        Mockito.doReturn(userPreferenceFromRepository).when(repository).get();

        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.FAHRENHEIT);
        interactor.saveTemperatureUnit(TemperatureUnitEnum.FAHRENHEIT);
        Mockito.verify(repository).save(Mockito.eq(userPreference));
    }

    @Test
    public void get(){
        UserPreference userPreference = UserPreferenceTestUtil.create();

        Mockito.doReturn(userPreference).when(repository).get();
        UserPreference userPreferenceResult = interactor.get();

        Assert.assertEquals(userPreference, userPreferenceResult);
    }

    @Test
    public void get_empty(){
        UserPreference userPreference = new UserPreference();

        Mockito.doReturn(userPreference).when(repository).get();
        UserPreference userPreferenceResult = interactor.get();

        Assert.assertEquals(userPreference, userPreferenceResult);
    }
}