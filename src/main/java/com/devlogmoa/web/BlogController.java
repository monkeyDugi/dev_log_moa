package com.devlogmoa.web;

import com.devlogmoa.domain.BlogDetail;
import com.devlogmoa.repository.BlogDetailRepository;
import com.devlogmoa.web.dto.response.BlogDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogDetailRepository blogDetailRepository;

    @GetMapping("/")
    public String blogs(Model model, @PageableDefault(page = 0, size = 2, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BlogDetailDto> blogDetails = blogDetailRepository.findAllByOrderByPubDateDesc(pageable)
                                                            .map(BlogDetailDto::new);

        model.addAttribute("blogDetails", blogDetails);

        return "blogList";
    }
}
