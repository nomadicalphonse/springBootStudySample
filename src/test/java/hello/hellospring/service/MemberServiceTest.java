package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach
    public void beforeEach(){//각 @Test가 실행되기전에 실행됩니다.
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach //@Test가 실행되어서 끝날때 마다 실행됩니다.
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("회원가입 join")
    void join() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    @DisplayName("중복회원예외처리 DuplicateM")
    void DuplicateTest() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        Long saveId = memberService.join(member1);

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        memberService.join(member1);
        try{
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */
    }

    @Test
    @DisplayName("회원목록 findMembers")
    void findMembers() {
    }

    @Test
    @DisplayName("회원정보 findOne")
    void findOne() {
    }
}