package com.doan.AppTuyenDung.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.doan.AppTuyenDung.DTO.DetailPostDTO;
import com.doan.AppTuyenDung.DTO.InfoPostDetailDto;
import com.doan.AppTuyenDung.entity.Post;

@Repository
public interface PostRepositoriesQuery extends JpaRepository<Post,Integer>{
    @Query(value =" SELECT pd.code_job_type as code_job_type,COUNT(pd.code_job_type) as amount, "+
    "a.value as value, "+
    "a.code as code, " +
    "a.image as image "+
    "FROM posts P " +
    "JOIN detailposts pd ON p.detail_post_id = pd.id " +
    "JOIN code_job_type a ON pd.code_job_type = a.code " +
    "WHERE P.status_Code = 'PS1' "+
    "GROUP BY pd.code_job_type, a.value, a.code, a.image "+
    "ORDER BY amount DESC ",nativeQuery = true)
    Page<Object[]> findPostJobTypeAndCountPost(Pageable pageable);

    @Query(value = "SELECT dp.id as id , dp.name as NameValue, ac1.value as CategoryJobCodeValue , ac2.value as CategoryJobLevelCodeValue ,\n" + //
                "ac3.value as CategoryWorktypeCodeValue , \n" + //
                "ac4.value as ExperienceJobCodeValue,\n" + //
                "ac5.value as GenderPostCodeValue , \n" + //
                "ac6.value as AddressCodeValue,\n" + //
                "ac7.value as SalaryCodeValue,\n" + //
                "c.thumbnail as CompanyThumbnailValue,\n" + //
                "p.time_Post as TimePostValue \n"+
                "FROM Posts p \n" + //
                "JOIN detailposts dp ON dp.id = p.detail_post_id \n" + //
                "JOIN companies c ON c.user_id = p.user_id \n " + //
                "LEFT JOIN code_job_type ac1 ON dp.code_job_type = ac1.code \n " + //
                "LEFT JOIN code_job_level ac2 ON dp.code_job_level = ac2.code \n " + //
                "LEFT JOIN code_work_type ac3 ON dp.code_work_type = ac3.code \n " + //
                "LEFT JOIN code_exp_type ac4 ON dp.code_exp_type = ac4.code \n " + //
                "LEFT JOIN code_gender_post ac5 ON dp.code_gender_post = ac5.code \n " + //
                "LEFT JOIN code_province ac6 ON dp.code_adress_code = ac6.code \n " + //
                "LEFT JOIN code_salary_type ac7 ON dp.code_salary_type = ac7.code \n " + //
                "WHERE p.status_Code = 'PS1' \n" + //
                "AND ( :categoryJobCode is null or ac1.code = :categoryJobCode ) \n " + //
                "AND ( :addressCode is null or ac6.code = :addressCode )\n" + //
                "AND ( :search is null or dp.name like '%:search%' )\n" + //
                "AND ( :experienceJobCodes is null or ac4.code in (:experienceJobCodes) )\n" + //
                "AND ( :categoryWorktypeCodes is null or ac3.code in (:categoryWorktypeCodes) )\n" + //
                "AND ( :salaryJobCodes is null or ac7.code in (:salaryJobCodes) )\n" + //
                "AND ( :categoryJoblevelCodes is null or ac2.code in (:categoryJoblevelCodes) )\n" + //
                "AND ( :isHot is null or p.is_hot = :isHot )\n" + //
                "ORDER BY p.time_Post DESC",
        nativeQuery = true)
    Page<DetailPostDTO> findFilteredPosts(@Param("categoryJobCode") String categoryJobCode,
                                           @Param("addressCode") String addressCode,
                                           @Param("search") String search,
                                           @Param("experienceJobCodes") List<String> experienceJobCodes,
                                           @Param("categoryWorktypeCodes") List<String> categoryWorktypeCodes,
                                           @Param("salaryJobCodes") List<String> salaryJobCodes,
                                           @Param("categoryJoblevelCodes") List<String> categoryJoblevelCodes,
                                           @Param("isHot") Integer isHot,
                                          Pageable pageable);


    @Query(value = "SELECT p.id as Id, p.time_Post as TimePostValue, p.time_End as TimeEndValue, dp.name, dp.descriptionhtml as DescriptionHTMLValue, " +
    "dp.description_markdown as DescriptionMarkdownValue, ac1.value as CategoryJobCodeValue, ac2.value as CategoryJobLevelCodeValue, " +
    "ac3.value as CategoryWorktypeCodeValue , ac4.value as ExperienceJobCodeValue, ac5.value as GenderPostCodeValue, " +
    "ac6.value as AdressCodeValue, ac7.value as SalaryCodeValue, c.name as NameCompanyValue, c.address as AddressCompanyValue, " +
    "c.cover_image as CoverImageCompanyValue, c.phonenumber as PhoneCompanyValue, c.taxnumber as TaxNumberValue, c.thumbnail as ThumbnailCompanyValue, " +
    "c.website as WebsiteCompanyValue, c.amount_employer as EmployerCompanyValue " +
    "FROM Posts p " +
    "JOIN detailposts dp ON dp.id = p.detail_post_id " +
    "JOIN companies c ON c.user_id = p.user_id " +
    "LEFT JOIN code_job_type ac1 ON dp.code_job_type = ac1.code " +
    "LEFT JOIN code_job_level ac2 ON dp.code_job_level = ac2.code " +
    "LEFT JOIN code_work_type ac3 ON dp.code_work_type = ac3.code " +
    "LEFT JOIN code_exp_type ac4 ON dp.code_exp_type = ac4.code " +
    "LEFT JOIN code_gender_post ac5 ON dp.code_gender_post = ac5.code " +
    "LEFT JOIN code_province ac6 ON dp.code_adress_code = ac6.code " +
    "LEFT JOIN code_salary_type ac7 ON dp.code_salary_type = ac7.code " +
    "WHERE p.id = :id", nativeQuery = true)
    List<InfoPostDetailDto> findPostDetailById(@Param("id") Integer id);
    
    
} 
