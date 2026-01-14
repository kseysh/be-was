package handler;

import db.*;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.BadRequestException;
import exception.HttpException;
import exception.UnauthorizedException;
import http.converter.HttpMessageConverter;
import http.converter.HttpMessageConverterMapper;
import http.converter.ImageForm;
import http.converter.MultipartData;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import model.Image;
import model.User;
import webserver.view.TemplateView;
import webserver.view.View;

public class MyPageHandler extends AbstractHandler{

    private final UserDatabase userDatabase = DatabaseConfig.userDatabase;
    private final ImageDatabase imageDatabase = DatabaseConfig.imageDatabase;

    public MyPageHandler() {}

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        String sessionId = request.getCookieValue("sid");
        User user = SessionManager.getInstance().getAttribute(sessionId).orElseThrow(() -> new UnauthorizedException("unauthorized"));

        Image userProfileImage = imageDatabase.findByIdOrThrow(user.getProfileImageId());

        Map<String,Object> model = new HashMap<>();
        model.put("name",user.getName());
        model.put("image", userProfileImage.toImageString());

        View view = new TemplateView("/mypage/index.html");
        view.render(model, request, response);
    }

    @Override
    protected void patch(HttpRequest request, HttpResponse response) throws HttpException {
        String sessionId = request.getCookieValue("sid");
        User user = SessionManager.getInstance().getAttribute(sessionId).orElseThrow(() -> new UnauthorizedException("unauthorized"));

        HttpMessageConverter<MultipartData> converter =
                HttpMessageConverterMapper.findHttpMessageConverter(MultipartData.class, request.getContentType());
        MultipartData multipartData = converter.read(request);

        ImageForm imageForm = multipartData.getFileBytes("image");
        String nickname = multipartData.getTexts("nickname");
        String password = multipartData.getTexts("password");
        String passwordConfirm = multipartData.getTexts("passwordConfirm");

        validateParameters(imageForm, nickname, password, passwordConfirm);

        String profileImageId = user.getProfileImageId();
        user.changeUserName(nickname);
        user.changePassword(password);

        imageDatabase.save(Image.of(profileImageId, imageForm));
        userDatabase.save(user);

        response.setStatusCode(HttpStatus.FOUND)
                .setHeader(HttpHeader.LOCATION.getValue(), "/")
                .setHeader(HttpHeader.CONTENT_LENGTH.getValue(), "0");
    }

    private void validateParameters(ImageForm imageForm, String nickname, String password, String passwordConfirm) {
        if(imageForm == null) throw new BadRequestException("imageForm required");
        if(nickname == null) throw new BadRequestException("nickname required");
        if(password == null) throw new BadRequestException("password required");
        if(!password.equals(passwordConfirm)) throw new BadRequestException("password not match");
    }
}
