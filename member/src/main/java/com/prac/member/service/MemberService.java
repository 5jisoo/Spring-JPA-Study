package com.prac.member.service;

import com.prac.member.dto.MemberDTO;
import com.prac.member.entity.Member;
import com.prac.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // dto -> entity 변환
        Member member = Member.toMemberEntity(memberDTO);

        // save 메서드 호출
        memberRepository.save(member);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<Member> byEmail = memberRepository.findByEmail(memberDTO.getEmail());
        if (byEmail.isPresent()) {
            // 조회 결과과 있다. -> 회원 정보 존재
            Member member = byEmail.get();
            if (member.getPassword().equals(memberDTO.getPassword())) {
                // 비밀번호 일치 -> DTO 객체 반환
                MemberDTO dto = MemberDTO.toMemberDTO(member);
                return dto;
            } else{
                // 비밀번호 불일치 (로그인 실패)
                return null;
            }
        } else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : memberList) {
            memberDTOList.add(MemberDTO.toMemberDTO(member));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return MemberDTO.toMemberDTO(member.get());
        } else{
            return null;
        }

    }

    public MemberDTO updateForm(String loginEmail) {
        Optional<Member> optionalMember = memberRepository.findByEmail(loginEmail);
        if (optionalMember.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMember.get());
        }else{
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(Member.toUpdateMemberEntity(memberDTO));

    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
