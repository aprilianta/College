package com.grd.adbo_admin.Retrofit;

import com.grd.adbo_admin.Model.DosenModel;
import com.grd.adbo_admin.Model.KajurModel;
import com.grd.adbo_admin.Model.ValueModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("insert.php")
    Call<ValueModel> daftar(@Field("nim") String npm,
                            @Field("nama") String nama,
                            @Field("jenis_kelamin") String jenis_kelamin,
                            @Field("semester") String semester,
                            @Field("nip") int nip,
                            @Field("pin") String pin);
    @GET("view.php")
    Call<ValueModel> view();

    @FormUrlEncoded
    @POST("update.php")
    Call<ValueModel> ubah(@Field("nim") String npm,
                          @Field("nama") String nama,
                          @Field("jenis_kelamin") String jenis_kelamin,
                          @Field("semester") String semester,
                          @Field("nip") int nip,
                          @Field("pin") String pin);
    @FormUrlEncoded
    @POST("delete.php")
    Call<ValueModel> hapus(@Field("nim") String nim);

    @GET("view_dosen.php")
    Call<DosenModel> getSemuaDosen();

    @FormUrlEncoded
    @POST("insert_dosen.php")
    Call<DosenModel> daftar_dosen(@Field("nip") String nip,
                            @Field("nama") String nama,
                            @Field("jenis_kelamin") String jenis_kelamin,
                            @Field("pin") String pin);

    @FormUrlEncoded
    @POST("update_dosen.php")
    Call<DosenModel> ubah_dosen(@Field("nip") String nip,
                          @Field("nama") String nama,
                          @Field("jenis_kelamin") String jenis_kelamin,
                          @Field("pin") String pin);

    @FormUrlEncoded
    @POST("delete_dosen.php")
    Call<DosenModel> hapus_dosen(@Field("nip") String nip);

    @GET("view_kajur.php")
    Call<KajurModel> view_kajur();

    @FormUrlEncoded
    @POST("insert_kajur.php")
    Call<KajurModel> insert_kajur(@Field("nip") String nip,
                                  @Field("nama") String nama,
                                  @Field("jenis_kelamin") String jenis_kelamin,
                                  @Field("pin") String pin);

    @FormUrlEncoded
    @POST("update_kajur.php")
    Call<KajurModel> ubah_kajur(@Field("nip") String nip,
                                @Field("nama") String nama,
                                @Field("jenis_kelamin") String jenis_kelamin,
                                @Field("pin") String pin);

    @FormUrlEncoded
    @POST("delete_kajur.php")
    Call<KajurModel> hapus_kajur(@Field("nip") String nip);
}
