package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.*;
import maker.server.Entity.GetUserPasswordResponse;
import maker.server.Entity.Response;
import maker.server.Entity.GetUserLoginResponse;
import maker.server.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity addUser(UserDto user) {
        try {
            authRepository.save(user);
            return new ResponseEntity(new Response(200, "회원가입 성공"), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new Response(404, "회원가입 실패"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getUserToken(UserLoginDto user) {
        int userIdx = authRepository.findUserIdx(user);
        String jwt =  jwtUtil.createJWT(userIdx, 3*60*60);
        return new ResponseEntity(new GetUserLoginResponse(jwt, 200,"토큰 생성 성공"), HttpStatus.OK);
    }

    public ResponseEntity getPassword(UserFindDto user) {
        String password = authRepository.findPassword(user);
        return new ResponseEntity(new GetUserPasswordResponse(password, 200, "비밀번호 찾기 성공"),HttpStatus.OK);
    }

    public ResponseEntity updateUser(UserUpdateDto user) {
        Integer userIdx = jwtUtil.parseJwt(user.getUserToken()).getBody().get("userIdx",Integer.class);
        authRepository.update(userIdx, user);
        return new ResponseEntity(new Response(200, "회원 정보 수정 성공"), HttpStatus.OK);
    }

    public ResponseEntity deleteUser(UserDeleteDto user) {
        Integer userIdx = jwtUtil.parseJwt(user.getUserToken()).getBody().get("userIdx",Integer.class);
        authRepository.delete(userIdx);
        return new ResponseEntity(new Response(200, "회원 탈퇴 성공"), HttpStatus.OK);
    }

    public ResponseEntity userIdCheck(String userId) {
        try{
            authRepository.userIdCheck(userId);
            return new ResponseEntity(new Response(400, "이미 사용중인 아이디입니다."), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new Response(200, "사용 가능한 아이디입니다."), HttpStatus.OK);
        }
    }
}
