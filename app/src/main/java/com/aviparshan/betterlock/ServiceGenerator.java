package com.aviparshan.betterlock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit retrofit;
    private static Gson gson;

//    int cacheSize = 10 * 1024 * 1024; // 10 MB
//    Cache cache = new Cache(this.getCacheDir(), cacheSize);
//
//    OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .cache(cache)
//            .build();

    public static Retrofit getRetrofit() {
        gson=new GsonBuilder()
                .create();

        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                        .baseUrl(CONSTANTS.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }

//    [{"person":{"id":1,"url":"https://www.tvmaze.com/people/1/mike-vogel","name":"Mike Vogel","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1979-07-17","deathday":null,"gender":"Male","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/1815.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/1815.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/1"}}},"character":{"id":1,"url":"https://www.tvmaze.com/characters/1/under-the-dome-dale-barbie-barbara","name":"Dale \"Barbie\" Barbara","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/3.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/3.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/1"}}},"self":false,"voice":false},{"person":{"id":2,"url":"https://www.tvmaze.com/people/2/rachelle-lefevre","name":"Rachelle Lefevre","country":{"name":"Canada","code":"CA","timezone":"America/Halifax"},"birthday":"1979-02-01","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/82/207417.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/82/207417.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/2"}}},"character":{"id":2,"url":"https://www.tvmaze.com/characters/2/under-the-dome-julia-shumway","name":"Julia Shumway","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/6.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/6.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/2"}}},"self":false,"voice":false},{"person":{"id":3,"url":"https://www.tvmaze.com/people/3/alexander-koch","name":"Alexander Koch","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1988-02-24","deathday":null,"gender":"Male","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/3/7814.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/3/7814.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/3"}}},"character":{"id":3,"url":"https://www.tvmaze.com/characters/3/under-the-dome-junior-rennie","name":"Junior Rennie","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/10.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/10.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/3"}}},"self":false,"voice":false},{"person":{"id":5,"url":"https://www.tvmaze.com/people/5/colin-ford","name":"Colin Ford","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1996-09-12","deathday":null,"gender":"Male","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/142/356748.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/142/356748.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/5"}}},"character":{"id":5,"url":"https://www.tvmaze.com/characters/5/under-the-dome-joe-mcalister","name":"Joe McAlister","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/7.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/7.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/5"}}},"self":false,"voice":false},{"person":{"id":7,"url":"https://www.tvmaze.com/people/7/mackenzie-lintz","name":"Mackenzie Lintz","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1996-11-22","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/3/7816.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/3/7816.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/7"}}},"character":{"id":7,"url":"https://www.tvmaze.com/characters/7/under-the-dome-norrie-calvert-hill","name":"Norrie Calvert-Hill","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/793.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/793.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/7"}}},"self":false,"voice":false},{"person":{"id":9,"url":"https://www.tvmaze.com/people/9/dean-norris","name":"Dean Norris","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1963-04-08","deathday":null,"gender":"Male","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/163/408986.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/163/408986.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/9"}}},"character":{"id":9,"url":"https://www.tvmaze.com/characters/9/under-the-dome-james-big-jim-rennie","name":"James \"Big Jim\" Rennie","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/2.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/2.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/9"}}},"self":false,"voice":false},{"person":{"id":4,"url":"https://www.tvmaze.com/people/4/eddie-cahill","name":"Eddie Cahill","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1978-01-15","deathday":null,"gender":"Male","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/1162.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/1162.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/4"}}},"character":{"id":4,"url":"https://www.tvmaze.com/characters/4/under-the-dome-sam-verdreaux","name":"Sam Verdreaux","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/17/44108.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/17/44108.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/4"}}},"self":false,"voice":false},{"person":{"id":6,"url":"https://www.tvmaze.com/people/6/nicholas-strong","name":"Nicholas Strong","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":null,"deathday":null,"gender":"Male","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/2499.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/2499.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/6"}}},"character":{"id":6,"url":"https://www.tvmaze.com/characters/6/under-the-dome-phil-bushey","name":"Phil Bushey","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/5.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/5.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/6"}}},"self":false,"voice":false},{"person":{"id":11,"url":"https://www.tvmaze.com/people/11/britt-robertson","name":"Britt Robertson","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1990-04-18","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/158/396055.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/158/396055.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/11"}}},"character":{"id":11,"url":"https://www.tvmaze.com/characters/11/under-the-dome-angie-mcalister","name":"Angie McAlister","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/4.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/4.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/11"}}},"self":false,"voice":false},{"person":{"id":12,"url":"https://www.tvmaze.com/people/12/aisha-hinds","name":"Aisha Hinds","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1975-11-13","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/1/2665.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/1/2665.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/12"}}},"character":{"id":12,"url":"https://www.tvmaze.com/characters/12/under-the-dome-carolyn-hill","name":"Carolyn Hill","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/8.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/8.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/12"}}},"self":false,"voice":false},{"person":{"id":10,"url":"https://www.tvmaze.com/people/10/natalie-martinez","name":"Natalie Martinez","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1984-07-12","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/1753.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/1753.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/10"}}},"character":{"id":10,"url":"https://www.tvmaze.com/characters/10/under-the-dome-deputy-linda-esquivel","name":"Deputy Linda Esquivel","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/792.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/792.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/10"}}},"self":false,"voice":false},{"person":{"id":35903,"url":"https://www.tvmaze.com/people/35903/kylie-bunbury","name":"Kylie Bunbury","country":{"name":"Canada","code":"CA","timezone":"America/Halifax"},"birthday":"1989-01-30","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/3/8789.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/3/8789.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/35903"}}},"character":{"id":140786,"url":"https://www.tvmaze.com/characters/140786/under-the-dome-eva-sinclair","name":"Eva Sinclair","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/17/44109.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/17/44109.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/140786"}}},"self":false,"voice":false},{"person":{"id":8,"url":"https://www.tvmaze.com/people/8/karla-crome","name":"Karla Crome","country":{"name":"United Kingdom","code":"GB","timezone":"Europe/London"},"birthday":"1989-06-22","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/3/7817.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/3/7817.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/8"}}},"character":{"id":8,"url":"https://www.tvmaze.com/characters/8/under-the-dome-rebecca-pine","name":"Rebecca Pine","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/794.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/794.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/8"}}},"self":false,"voice":false},{"person":{"id":13,"url":"https://www.tvmaze.com/people/13/jolene-purdy","name":"Jolene Purdy","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1983-12-09","deathday":null,"gender":"Female","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/2/5993.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/2/5993.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/13"}}},"character":{"id":13,"url":"https://www.tvmaze.com/characters/13/under-the-dome-dodee-weaver","name":"Dodee Weaver","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/9.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/9.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/characters/13"}}},"self":false,"voice":false},{"person":{"id":14,"url":"https://www.tvmaze.com/people/14/jeff-fahey","name":"Jeff Fahey","country":{"name":"United States","code":"US","timezone":"America/New_York"},"birthday":"1952-11-29","deathday":null,"gender":"Male","image":{"medium":"https://static.tvmaze.com/uploads/images/medium_portrait/0/1163.jpg","original":"https://static.tvmaze.com/uploads/images/original_untouched/0/1163.jpg"},"_links":{"self":{"href":"https://api.tvmaze.com/people/14"}}},"character":{"id":14,"url":"https://www.tvmaze.com/characters/14/under-the-dome-sheriff-duke-perkins","name":"Sheriff Duke Perkins","image":null,"_links":{"self":{"href":"https://api.tvmaze.com/characters/14"}}},"self":false,"voice":false}]
}
