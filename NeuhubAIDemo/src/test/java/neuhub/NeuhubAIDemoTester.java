package neuhub;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import neuhub.properties.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

/**
 * {@link NeuhubAIDemoTester#asr()} 语音识别接口
 * {@link NeuhubAIDemoTester#comment()} 评论观点抽取接口
 * {@link NeuhubAIDemoTester#facePropV1()} 人脸检测与属性分析接口
 * {@link NeuhubAIDemoTester#detectHacknessV1()} 人脸活体检测接口
 * {@link NeuhubAIDemoTester#foodApi()} 菜品识别接口
 * {@link NeuhubAIDemoTester#humanDetect()} 人体检测接口
 * {@link NeuhubAIDemoTester#ocrIdCard()} 身份证识别接口
 * {@link NeuhubAIDemoTester#ocrInvoice()} 增值税发票识别接口
 * {@link NeuhubAIDemoTester#politiciansRecognition()} 特定人物识别接口
 * {@link NeuhubAIDemoTester#lexer()} 词法分析接口
 * {@link NeuhubAIDemoTester#poseEstimation()} 人体关键点检测接口
 * {@link NeuhubAIDemoTester#faceGroupCreate()} 创建人脸分组接口
 * {@link NeuhubAIDemoTester#groupFetchV1()} 获取分组列表
 * {@link NeuhubAIDemoTester#faceGroupDelete()} 删除人脸分组接口
 * {@link NeuhubAIDemoTester#faceCreate()} 创建人脸接口
 * {@link NeuhubAIDemoTester#faceDeleteV1()} 删除人脸接口
 * {@link NeuhubAIDemoTester#faceUpdateV1()} 修改人脸接口
 * {@link NeuhubAIDemoTester#faceFetchV1()} 获取人脸列表接口
 * {@link NeuhubAIDemoTester#searchFace()} 人脸搜索接口
 * {@link NeuhubAIDemoTester#selfieSegmentation()} 自拍人像抠图接口
 * {@link NeuhubAIDemoTester#sentiment()} 情感分析接口
 * {@link NeuhubAIDemoTester#sexyGet()} 智能鉴黄(GET请求)接口
 * {@link NeuhubAIDemoTester#localCvImage()} 智能鉴黄(POST请求)接口
 * {@link NeuhubAIDemoTester#similarity()} 短文本相似度接口
 * {@link NeuhubAIDemoTester#snapShop()} 拍照购接口
 * {@link NeuhubAIDemoTester#textClassification()} 文本分类接口
 * {@link NeuhubAIDemoTester#systax()} 句法分析接口
 * {@link NeuhubAIDemoTester#tts()} 语音合成接口
 * {@link NeuhubAIDemoTester#ocrUniversal()} 通用文字识别接口
 * {@link NeuhubAIDemoTester#ocrVehicleRecognition()} 行驶证识别接口
 * {@link NeuhubAIDemoTester#ocrBankcard()} 银行卡识别接口
 * {@link NeuhubAIDemoTester#ocrBusiness()} 营业执照识别接口
 * {@link NeuhubAIDemoTester#garbageVoiceSearch()} 垃圾分类语音识别接口
 * {@link NeuhubAIDemoTester#garbageTextSearch()} 垃圾分类文本识别接口
 * {@link NeuhubAIDemoTester#humanParsing()} 细粒度人像分割接口
 * {@link NeuhubAIDemoTester#image_recognition_product()} 通用商品识别接口
 * {@link NeuhubAIDemoTester#vehicleDetection()} 车辆检测接口
 * {@link NeuhubAIDemoTester#imageSearchIndex()} 通用图片搜索图片入库
 * {@link NeuhubAIDemoTester#imageSearchTask()} 通用图片搜索任务状态查询
 * {@link NeuhubAIDemoTester#extract_img_colors()} 颜色识别
 * {@link NeuhubAIDemoTester#detect_shelf_product()} 货架商品检测
 * {@link NeuhubAIDemoTester#recommend_outfits()} 搭配生成
 * {@link NeuhubAIDemoTester#product_image_create_product()} 商品图片搜索_创建商品
 * {@link NeuhubAIDemoTester#product_image_add()} 商品图片搜索_商品图入库
 * {@link NeuhubAIDemoTester#product_image_status()} 商品图片搜索_入库状态查询
 * {@link NeuhubAIDemoTester#product_image_search()} 商品图片搜索_商品图片搜索
 * {@link NeuhubAIDemoTester#product_image_info()} 商品图片搜索_商品图片信息查询
 * {@link NeuhubAIDemoTester#product_image_del_image()} 商品图片搜索_商品图片删除
 * {@link NeuhubAIDemoTester#product_image_del_product()} 商品图片搜索_商品删除
 * {@link NeuhubAIDemoTester#product_image_del_collection()} 商品图片搜索_商品库删除
 * {@link NeuhubAIDemoTester#medical_material_recognize()} 疫情物资识别
 * {@link NeuhubAIDemoTester#marketSegmentationAnalyse()} 细分市场划分
 * {@link NeuhubAIDemoTester#brandCompeteAnalyse()} 品牌竞争力分析
 * {@link NeuhubAIDemoTester#productUpgrade()} 老品升级
 * {@link NeuhubAIDemoTester#productCustom()} 新品定制
 * {@link NeuhubAIDemoTester#compete_analysis()} 商品竞争力分析
 * {@link NeuhubAIDemoTester#nmt()} 机器翻译
 * {@link NeuhubAIDemoTester#similar()} 词义相似度
 * {@link NeuhubAIDemoTester#word_vector()} 词向量
 * {@link NeuhubAIDemoTester#phrase_extract()} 短语挖掘
 * {@link NeuhubAIDemoTester#create_article()} 智能写作
 * {@link NeuhubAIDemoTester#chatbot()} 闲聊
 * {@link NeuhubAIDemoTester#language_smooth()} 文本流畅度
 * {@link NeuhubAIDemoTester#corpus_synonyms()} 同义词
 * {@link NeuhubAIDemoTester#corpus_aggregation()} 聚类
 * {@link NeuhubAIDemoTester#language_cloze()} 语言模型填空
 * {@link NeuhubAIDemoTester#hotwords()} 新闻热词抽取
 * {@link NeuhubAIDemoTester#ocr_driver_license()} 驾驶证识别
 * {@link NeuhubAIDemoTester#ocr_business()} 营业执照识别
 * {@link NeuhubAIDemoTester#ocr_bankcard()} 银行卡识别
 * {@link NeuhubAIDemoTester#fasr_create()} 录音文件识别_创建
 * {@link NeuhubAIDemoTester#fasr_query()} 录音文件识别_查询
 * {@link NeuhubAIDemoTester#vpr()} 声纹识别
 * {@link NeuhubAIDemoTester#rt_asr()} 实时语音识别
 * {@link NeuhubAIDemoTester#mask_detect()} 人脸口罩识别
 * {@link NeuhubAIDemoTester#faceCompareV1()} 人脸对比
 * {@link NeuhubAIDemoTester#personreid()} 行人重识别接口
 * {@link NeuhubAIDemoTester#face_parser()} 人脸解析
 * {@link NeuhubAIDemoTester#censor_image()} 通用图片审核
 * {@link NeuhubAIDemoTester#censor_text()} 文本智能审核
 * {@link NeuhubAIDemoTester#censor_ad()} 广告检测
 * {@link NeuhubAIDemoTester#index()} 通用图片搜索_图片入库
 * {@link NeuhubAIDemoTester#task()} 通用图片搜索_任务状态查询
 * {@link NeuhubAIDemoTester#index_search()} 通用图片搜索_图片搜索
 * {@link NeuhubAIDemoTester#collection_list()} 通用图片搜索_库列表查询
 * {@link NeuhubAIDemoTester#image_delete()} 通用图片搜索_图片删除
 * {@link NeuhubAIDemoTester#image_update()} 通用图片搜索_图片信息更新
 * {@link NeuhubAIDemoTester#image_fetch()} 通用图片搜索_图片信息查询
 * {@link NeuhubAIDemoTester#license_plate()} 车牌识别
 * {@link NeuhubAIDemoTester#garbageImageSearch()} 垃圾分类
 * {@link NeuhubAIDemoTester#vehicle_detection()} 车辆检测
 * {@link NeuhubAIDemoTester#car_class()} 车型识别
 * {@link NeuhubAIDemoTester#dog_class()} 犬类识别
 * {@link NeuhubAIDemoTester#vehicle_parser()} 车辆解析
 * {@link NeuhubAIDemoTester#intelligent_triage()} 智能分诊
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NeuhubAIDemoApplication.class)
public class NeuhubAIDemoTester {

    private Logger logger = LoggerFactory.getLogger(NeuhubAIDemoTester.class);
    private RestTemplate restTemplate;
    private ClientCredentialsResourceDetails clientCredentialsResourceDetails;

    /**
     * 这不是一个普通的RestTemplate，而是引用的OAuth2RestTemplate
     *
     * @param restTemplate
     */
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Autowired
    public void setClientCredentialsResourceDetails(ClientCredentialsResourceDetails clientCredentialsResourceDetails) {
        this.clientCredentialsResourceDetails = clientCredentialsResourceDetails;
    }

    /**
     * 调用的api网关地址
     */
    @Value("${gateway.url}")
    private String gatewayUrl;

    /**
     * 测试时使用的图片位置，在配置文件中进行修改
     */
    @Value("${neuhub.picture}")
    private String picture;

    /**
     * 人脸对比接口测试时使用的图片位置，在配置文件中进行修改
     */
    @Value("${neuhub.pictureCompare}")
    private String pictureCompare;

    /**
     * 测试时使用的文本内容，在配置文件中进行修改
     */
    @Value("${neuhub.comment}")
    private String comment;

    /**
     * 测试短文本相似度接口的文本内容，在配置文件中进行修改
     */
    @Value("${neuhub.commentCompare}")
    private String commentCompare;

    /**
     * 测试之前进行环境检查，确保输入正确的clientId和clientSecret
     */
//    @Before
//    public void checkEnvironment() {
//        int clientIdLength = 32;
//        int clientSecretLength = 10;
//        if (clientCredentialsResourceDetails.getClientId() == null || clientCredentialsResourceDetails.getClientId().length() != clientIdLength) {
//            logger.error("clientId有误，请重新填写");
//            System.exit(1);
//        }
//
//        if (clientCredentialsResourceDetails.getClientSecret() == null || clientCredentialsResourceDetails.getClientSecret().length() != clientSecretLength) {
//            logger.error("clientSecret有误，请重新填写");
//            System.exit(1);
//        }
//    }
    @Test
    public void humanDetect() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/human_detect";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void poseEstimation() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        //muti_det为单人姿态(1)或多人姿态(2)
        int muti_det = 1;
        String requestUrl = gatewayUrl + "/neuhub/pose_estimation?muti_det={muti_det}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, muti_det);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void detectHacknessV1() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/detectHacknessV1";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisual", true);
        map.put("imgBase64Visual", encodedText);
        map.put("imgBase64Nir", encodedText);
        HttpEntity requestEntity = new HttpEntity(map);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    @Test
    public void facePropV1() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);

        Map<String, Object> map = new HashMap<>();
        map.put("imgBase64", encodedText);
        HttpEntity requestEntity = new HttpEntity(map);
        String requestUrl = gatewayUrl + "/neuhub/facePropV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 创建人脸分组
     */
    @Test
    public void faceGroupCreate() {
        /**
         * groupName为用户创建分组的名称，根据分组名称完成创建后可以获得groupId，
         * 通过{@link NeuhubAIDemoTester#getFaceGroupList()} 查看分组信息
         */
        String groupName = "zhoujiaweiTest";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/groupCreateV1?groupName={groupName}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupName);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    /**
     * 获取分组列表
     */
    @Test
    public void groupFetchV1() {
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/groupFetchV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     *  创建人脸接口
     */
    @Test
    public void faceCreate() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);

        //调用 faceGroupCreate() 接口创建分组ID
        String groupId = "5f9698af6feeeab9f228b8aa";
        Map<String,Object>map = new HashMap();
        map.put("groupId",groupId);
        map.put("imgBase64",encodedText);

        //调用创建分组接口
        HttpEntity<Object> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/faceCreateV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     *删除人脸接口
     */
    @Test
    public void faceDeleteV1() {
        String faceId = "5f969a3f6feeeab9f228b8b8";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("faceId", faceId);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null);
        String requestUrl = gatewayUrl + "/neuhub/faceDeleteV1?faceId={faceId}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl,requestEntity, String.class,map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    /**
     * 修改人脸
     */
    @Test
    public void faceUpdateV1() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        String groupId = "5f9698af6feeeab9f228b8aa";
        String faceId = "5f969a3f6feeeab9f228b8b8";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("faceId", faceId);
        map.put("imgBase64", encodedText);
        map.put("groupId", groupId);
        HttpEntity<Object> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/faceUpdateV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl,requestEntity, String.class,map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    /**
     * 修改分组
     */
    @Test
    public void groupUpdateV1() {

        //调用 faceGroupCreate() 接口创建分组ID
        String groupId = "5f97800a88aaae165438cca6";
        String groupName = "zjwUpdate";
        Map<String,Object> map = new HashMap();
        map.put("groupId",groupId);
        map.put("groupName",groupName);
        //调用创建分组接口
        HttpEntity<Object> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/groupUpdateV1?groupId={groupId}&groupName={groupName}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class,map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    /**
     * 删除分组
     */
    @Test
    public void faceGroupDelete() {

        //分组ID
        String groupId = "5f96a0416feeeab9f228b8d7";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/groupDeleteV1?groupId={groupId}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupId);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 获取人脸
     */
    @Test
    public void faceFetchV1() {
        int pageId = 0;
        int pageSize = 10;
        String groupId = "5f9698af6feeeab9f228b8aa";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/faceFetchV1?groupId={groupId}&pageId={pageId}&pageSize={pageSize}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", groupId);
        map.put("pageId", pageId);
        map.put("pageSize", pageSize);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 人脸搜索接口
     */
    @Test
    public void searchFace() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);

        //调用 faceGroupCreate() 接口创建分组ID
        String groupId = "5f9698af6feeeab9f228b8aa";
        Map<String,Object>map = new HashMap();
        map.put("groupId",groupId);
        map.put("imgBase64",encodedText);

        HttpEntity<Object> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/faceSearchV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void sexyGet() {
        //image_url后为待测试的图片的路径
        String requestUrl = gatewayUrl + "/neuhub/cvImage?image_url=https://smartpart-public.s3.cn-north-1.jdcloud-oss.com/Demo1.jpg";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void localCvImage() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/localCvImage";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 菜品识别
     * @throws Exception
     */
    @Test
    public void foodApi() throws Exception {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);

        // TODO 菜品识别调用失败 返回11010
        Picture pic = new Picture(encodedText);
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(pic);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/FoodApi";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void politiciansRecognition() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/PoliticiansRecognition";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void selfieSegmentation() throws Exception {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        // Picture类为自创的实体类
        Picture pic = new Picture(encodedText);
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(pic);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/SelfieSeg";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void snapShop() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        //以下参数仅为示例值
        //todo channel_id 为test 需向管理员申请一个专属的
        String channelId = "test";
        String request = String.format("channel_id=%s&&imgBase64=%s", channelId, encodedText);
        HttpEntity<String> requestEntity = new HttpEntity<>(request);
        String requestUrl = gatewayUrl + "/neuhub/snapshop";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void comment() {
        Map<String, String> map = new HashMap<>();
        map.put("text", comment);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/CommentTag";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void lexer() {
        int type = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("text", comment);
        map.put("type", type);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/lexer";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void sentiment() {
        //type为情感模型的类型
        int type = 1;
        //body请求参数
        Sentiment sentiment = new Sentiment(type, comment);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sentiment> requestEntity = new HttpEntity<>(sentiment, httpHeaders);
        String requestUrl = gatewayUrl + "/neuhub/sentiment";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void similarity() {
        //body请求参数
        Map<String, Object> postParameters = new HashMap<>();
        postParameters.put("text1", comment);
        postParameters.put("text2", commentCompare);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(postParameters);
        String requestUrl = gatewayUrl + "/neuhub/similarity";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void systax() {
        Map<String, String> map = new HashMap<>();
        map.put("text", comment);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/parser";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void textClassification() {
        Map<String, String> map = new HashMap<>();
        map.put("text", comment);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/textClassification";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrIdCard() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_idcard";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrInvoice() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_invoice";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrUniversal() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_universal";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }


    @Test
    public void ocrVehicleRecognition() {
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_vehicle_recognition";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * domain为语音识别领域，其他参数值请参考文档，applicationId为产品ID，requestId为请求语音串标识码，
     * sequenceId为语音分段传输的分段号，asrProtocol为通信协议版本号，netState为
     * 客户端网络状态，applicator为应用者，AsrEncode构造函数后参数分别为音频声道
     * 数、音频格式和采样率，AsrProperty构造函数后参数为是否开启服务端自动断尾、
     * 语音识别的请求格式AsrEncode、各平台的机型信息和客户端版本号
     */
    @Test
    public void asr() {
        //header示例参数
        String domain = "search";
        String applicationId = "search-app";

        //TODO 没有调通
        //String requestId = "65845428-de85-11e8-9517-040973d59a1e";
        String requestId = UUID.randomUUID().toString();
        int sequenceId = -1;
        int asrProtocol = 1;
        int netState = 2;
        int applicator = 1;
        byte[] data = dataBinary(picture);
        AsrEncode asrEncode = new AsrEncode(1, "wav", 16000);
        AsrProperty property = new AsrProperty(false, asrEncode, "Linux", "0.0.0.1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Domain", domain);
        httpHeaders.set("Application-Id", applicationId);
        httpHeaders.set("Request-Id", requestId);
        httpHeaders.set("Sequence-Id", Integer.toString(sequenceId));
        httpHeaders.set("Asr-Protocol", Integer.toString(asrProtocol));
        httpHeaders.set("Net-State", Integer.toString(netState));
        httpHeaders.set("Applicator", Integer.toString(applicator));
        httpHeaders.set("property", property.toString());
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, httpHeaders);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/asr";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * serviceType为服务类型，requestId为请求语音串标识码，sequenceId为文本分段传输的分段号，
     * protocol为通信协议版本号，netState为客户端网络状态，applicator为应用者，TtsParameters
     * 构造函数后内容依次为文本编码格式 、音频编码格式、音色、音量、语速和采样率，TtsProperty构
     * 造函数后内容依次为各平台的机型信息、客户端版本号和TtsParameters
     */
    @Test
    public void tts() {
        String serviceType = "synthesis";

        //String requestId = "65845428-de85-11e8-9517-040973d59a1e";
        String requestId = UUID.randomUUID().toString();
        int sequenceId = 1;
        int protocol = 1;
        int netState = 1;
        int applicator = 1;
        TtsParameters ttsParameters = new TtsParameters("1", "1", "0", "2.0", "1.0", "24000");
        TtsProperty property = new TtsProperty("Linux", "0.0.0.1", ttsParameters);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Service-Type", serviceType);
        httpHeaders.set("Request-Id", requestId);
        httpHeaders.set("Sequence-Id", Integer.toString(sequenceId));
        httpHeaders.set("Protocol", Integer.toString(protocol));
        httpHeaders.set("Net-State", Integer.toString(netState));
        httpHeaders.set("Applicator", Integer.toString(applicator));
        httpHeaders.set("property", property.toString());

        Map <String,Object> map = new HashMap<String,Object>();
        map.put("body","你好，京东！");
        HttpEntity requestEntity = new HttpEntity(map, httpHeaders);

        String requestUrl = gatewayUrl + "/neuhub/tts";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrBankcard() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_bankcard";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void ocrBusiness() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/ocr_business";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    // @Test
    // public void garbageImageSearch() {
    //     byte[] data = dataBinary(picture);
    //     String imageBase64 = imageBase64(data);
    //     Map<String, Object> map = new HashMap<>();
    //     map.put("imgBase64", imageBase64);
    //     map.put("cityId", "310000");
    //     HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
    //     String requestUrl = gatewayUrl + "/neuhub/garbageImageSearch";
    //     ResponseEntity<String> responseEntity = null;
    //     try {
    //         responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
    //     } catch (Exception e) {
    //         //调用API失败，错误处理
    //         throw new RuntimeException(e);
    //     }
    //     result(responseEntity);
    // }

    @Test
    public void garbageVoiceSearch() {
        AsrEncode asrEncode = new AsrEncode(1, "amr", 16000, 0);
        AsrProperty property = new AsrProperty(false, asrEncode, "Linux", "0.0.0.1");
        String cityId = "310000";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("property", property.toString());
        httpHeaders.set("cityId", cityId);

        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, httpHeaders);
        //以下参数仅为示例值
        String requestUrl = gatewayUrl + "/neuhub/garbageVoiceSearch";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void garbageTextSearch() {
        Map<String, Object> map = new HashMap<>();
        map.put("text", comment);
        map.put("cityId", "310000");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        String requestUrl = gatewayUrl + "/neuhub/garbageTextSearch";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void humanParsing() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/human_parsing";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"items":[{"cid_list":[{"root":"上衣","score":0.97397727},{"root":"服饰内衣","score":0.9999118},{"root":"男装","score":0.96508706},{"root":"卫衣","score":0.98625517}],"rectangle":{"bottom":732.54833984375,"confidence":0.9975353479385376,"left":145.329833984375,"right":643.0061645507812,"top":113.27703857421875}}],"message":"Inference Succeed!","status":"0"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 11:50 上午
     */
    // @Test
    // public void personreid() {
    //     byte[] data = dataBinary(picture);
    //     HttpEntity<Object> requestEntity = new HttpEntity<>(data);
    //     String requestUrl = gatewayUrl + "/neuhub/personreid";
    //     ResponseEntity<String> responseEntity = null;
    //     try {
    //         responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
    //     } catch (Exception e) {
    //         //调用API失败，错误处理
    //         throw new RuntimeException(e);
    //     }
    //     result(responseEntity);
    // }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"items":[{"cid_list":[{"root":"配饰","score":0.9999292},{"root":"医药保健","score":0.9999993},{"root":"护理护具","score":0.99999964},{"root":"口罩","score":0.9999999}],"rectangle":{"bottom":325.3965377807617,"confidence":0.9167974591255188,"left":96.97844505310059,"right":417.9494857788086,"top":37.289772033691406}}],"message":"Inference Succeed!","status":"0"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:10 上午
     */
    @Test
    public void image_recognition_product() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/plain");
        String value = String.format("imgBase64=%s", encodedText);
        HttpEntity requestEntity = new HttpEntity<>(value, httpHeaders);


        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("imgBase64", encodedText);

        Map<String, Object> map = new HashMap<>();
        map.put("imgBase64", encodedText);
        // HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, httpHeaders);
        // String requestUrl = gatewayUrl + "/neuhub/productRecognize";
        String requestUrl = gatewayUrl + "/neuhub/image_recognition_product";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void vehicleDetection() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
        String requestUrl = gatewayUrl + "/neuhub/vehicle_detection";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void imageSearchIndex() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/index";
        List<Docs> docsList = new ArrayList<>();
        Docs docs = new Docs();
        docs.setImage_name("Milk_salt_soda_v1");
        docs.setImage_content(imageBase64);
        docs.setInfo("info");
        docsList.add(docs);
        Map<String, Object> map = new HashMap<>();
        map.put("collection_name", comment);
        map.put("docs", docsList);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void imageSearchTask() {
        String task_id = "3548/蔬菜/1576589636.5655253";
        String requestUrl = gatewayUrl + "/neuhub/task?task_id=" + task_id;

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }



    public static String encodeImgageToBase64(String url) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        File file = new File(url);
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * {"code":"10000","charge":true,"remain":974,"remainTimes":974,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"color_list":[{"hex":"#BAB2AC","percentage":0.3593597412109375,"rgb":"186,178,172"},{"hex":"#D4CABF","percentage":0.2919769287109375,"rgb":"212,202,191"},{"hex":"#A39E9B","percentage":0.2889862060546875,"rgb":"163,158,155"},{"hex":"#787070","percentage":0.041473388671875,"rgb":"120,112,112"},{"hex":"#494342","percentage":0.0182037353515625,"rgb":"73,67,66"}],"status_code":"0","status_message":"success"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 5:47 下午
     */
    @Test
    public void extract_img_colors() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/extract_img_colors";
        int color_count = 5;
        String value = "image=" + imageBase64 + "&color_count=" + color_count;
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity(value);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":995,"remainTimes":995,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"bboxes":[{"Confidence":0.20836183428764343,"bbox":[7,636,76,719],"class_name":"object"},{"Confidence":0.18810199201107025,"bbox":[33,638,96,719],"class_name":"object"},{"Confidence":0.1625920683145523,"bbox":[489,464,638,635],"class_name":"object"},{"Confidence":0.15883946418762207,"bbox":[125,728,179,795],"class_name":"object"},{"Confidence":0.15545311570167542,"bbox":[22,740,94,797],"class_name":"object"},{"Confidence":0.15373295545578003,"bbox":[573,475,648,648],"class_name":"object"},{"Confidence":0.14955675601959229,"bbox":[100,24,343,219],"class_name":"object"},{"Confidence":0.14159874618053436,"bbox":[57,642,118,720],"class_name":"object"},{"Confidence":0.14019979536533356,"bbox":[486,467,581,579],"class_name":"object"},{"Confidence":0.13248951733112335,"bbox":[0,638,41,721],"class_name":"object"},{"Confidence":0.13235361874103546,"bbox":[99,735,157,797],"class_name":"object"},{"Confidence":0.13051649928092957,"bbox":[656,505,708,681],"class_name":"object"},{"Confidence":0.1296822428703308,"bbox":[434,450,625,687],"class_name":"object"},{"Confidence":0.1291021853685379,"bbox":[150,728,203,795],"class_name":"object"},{"Confidence":0.1268327534198761,"bbox":[254,641,289,696],"class_name":"object"},{"Confidence":0.12598690390586853,"bbox":[11,626,121,716],"class_name":"object"},{"Confidence":0.12511569261550903,"bbox":[368,728,413,797],"class_name":"object"},{"Confidence":0.12410488724708557,"bbox":[629,723,684,795],"class_name":"object"},{"Confidence":0.12117989361286163,"bbox":[352,727,399,797],"class_name":"object"},{"Confidence":0.12105372548103333,"bbox":[269,641,305,695],"class_name":"object"},{"Confidence":0.11994653195142746,"bbox":[462,728,515,790],"class_name":"object"},{"Confidence":0.1194823831319809,"bbox":[239,641,272,696],"class_name":"object"},{"Confidence":0.11943613737821579,"bbox":[256,721,305,796],"class_name":"object"},{"Confidence":0.11774037033319473,"bbox":[213,652,251,699],"class_name":"object"},{"Confidence":0.11716287583112717,"bbox":[465,603,513,703],"class_name":"object"},{"Confidence":0.11682609468698502,"bbox":[239,720,288,797],"class_name":"object"},{"Confidence":0.11677271872758865,"bbox":[440,728,491,793],"class_name":"object"},{"Confidence":0.11646470427513123,"bbox":[52,739,121,797],"class_name":"object"},{"Confidence":0.11621517688035965,"bbox":[223,714,272,794],"class_name":"object"},{"Confidence":0.11618538200855255,"bbox":[326,726,378,797],"class_name":"object"},{"Confidence":0.11617251485586166,"bbox":[395,729,440,796],"class_name":"object"},{"Confidence":0.11570960283279419,"bbox":[262,616,296,696],"class_name":"object"},{"Confidence":0.11568797379732132,"bbox":[284,639,321,695],"class_name":"object"},{"Confidence":0.1155451238155365,"bbox":[653,723,708,795],"class_name":"object"},{"Confidence":0.11434587836265564,"bbox":[247,618,280,696],"class_name":"object"},{"Confidence":0.11410341411828995,"bbox":[485,728,540,789],"class_name":"object"},{"Confidence":0.11397726833820343,"bbox":[316,635,354,697],"class_name":"object"},{"Confidence":0.11388843506574631,"bbox":[606,724,661,796],"class_name":"object"},{"Confidence":0.11369414627552032,"bbox":[417,465,578,627],"class_name":"object"},{"Confidence":0.11273546516895294,"bbox":[333,636,371,697],"class_name":"object"},{"Confidence":0.11261255294084549,"bbox":[300,636,338,696],"class_name":"object"},{"Confidence":0.11242323368787766,"bbox":[164,737,218,798],"class_name":"object"},{"Confidence":0.11172764003276825,"bbox":[76,653,134,721],"class_name":"object"},{"Confidence":0.11077481508255005,"bbox":[349,636,387,698],"class_name":"object"},{"Confidence":0.11029742658138275,"bbox":[194,652,239,698],"class_name":"object"},{"Confidence":0.11029518395662308,"bbox":[278,612,312,696],"class_name":"object"},{"Confidence":0.11002067476511002,"bbox":[294,724,346,797],"class_name":"object"},{"Confidence":0.10923691093921661,"bbox":[424,728,470,794],"class_name":"object"},{"Confidence":0.10771875083446503,"bbox":[122,659,181,716],"class_name":"object"},{"Confidence":0.1076522096991539,"bbox":[523,729,584,788],"class_name":"object"},{"Confidence":0.10730298608541489,"bbox":[208,722,256,797],"class_name":"object"},{"Confidence":0.10725757479667664,"bbox":[326,614,362,698],"class_name":"object"},{"Confidence":0.1063307598233223,"bbox":[294,611,329,697],"class_name":"object"},{"Confidence":0.10604537278413773,"bbox":[452,465,489,561],"class_name":"object"},{"Confidence":0.10505066812038422,"bbox":[387,620,429,701],"class_name":"object"},{"Confidence":0.10501842945814133,"bbox":[231,620,264,695],"class_name":"object"},{"Confidence":0.10475017875432968,"bbox":[99,658,157,718],"class_name":"object"},{"Confidence":0.1041857972741127,"bbox":[270,715,322,794],"class_name":"object"},{"Confidence":0.10392242670059204,"bbox":[362,642,405,700],"class_name":"object"},{"Confidence":0.10381366312503815,"bbox":[404,615,446,703],"class_name":"object"},{"Confidence":0.10298680514097214,"bbox":[580,726,637,794],"class_name":"object"},{"Confidence":0.10267627239227295,"bbox":[228,655,266,700],"class_name":"object"},{"Confidence":0.10227160900831223,"bbox":[555,729,614,791],"class_name":"object"},{"Confidence":0.10172883421182632,"bbox":[671,718,721,791],"class_name":"object"},{"Confidence":0.10172398388385773,"bbox":[407,733,455,798],"class_name":"object"},{"Confidence":0.10012178868055344,"bbox":[477,593,531,698],"class_name":"object"}],"message":"Inference Succeed!","status":"0"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 5:51 下午
     */
    @Test
    public void detect_shelf_product() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/detect_shelf_product";
        String value = "imgBase64=" + imageBase64;
        HttpEntity requestEntity = new HttpEntity<>(value);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":997,"remainTimes":997,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"outfits":[{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126140/16/10811/100037/5f4486c5E43f165ba/df2220f6d30cb0e7.jpg","item_name":"℘十几岁初中生高中生18岁大学生穿的工装裤子男士秋季新款潮牌束脚休闲长裤潮流百搭宽松直筒九分男裤 浅蓝 L"}],"score":0.5115331980963349},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/131961/32/8165/100037/5f449f88Ed8a79eb8/584dd46e5b887e06.jpg","item_name":"2020年夏季休闲长裤男潮牌薄款韩版潮流百搭工装裤直筒宽松九分夏天裤子 6602黑色 L"}],"score":0.5115331980963349},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/128142/8/10855/99671/5f44a1fdEac8d830c/e877098ff6f17178.jpg","item_name":"木子胜春秋款男士休闲裤男宽松直筒九分裤男百搭潮流束脚裤子男 蓝色 S"}],"score":0.5108855821566454},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126743/11/10731/223936/5f44c0c3E281e44c8/6c2466c3e31678bd.jpg","item_name":"【旅游日常穿的】泰国大象裤男女情侣裤夏季灯笼裤宽松大码显瑜伽裤沙滩海边度假2020 绿色高腰(20年新款) 系带款加大(3尺5腰围内)"}],"score":0.4962124623021198},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/133670/21/8178/76082/5f44a251E8ddf72c0/d63ae197eb0b31dc.jpg","item_name":"伊云佳 破洞九分牛仔裤男印花烫钻浅灰色裤子弹力修身小脚休闲青年9分潮裤 883# 27(2尺)"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/120072/40/10756/63454/5f44809aEa8134c3c/1e5c392eccee720d.jpg","item_name":"花花公子2020春夏季新品松紧裤腰青年韩版收口小脚抽绳长裤潮流时尚百搭运动休闲裤男宽松直筒束脚裤 MF-8818咖啡色 3XL"}],"score":0.4961087151490199},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126140/16/10811/100037/5f4486c5E43f165ba/df2220f6d30cb0e7.jpg","item_name":"℘十几岁初中生高中生18岁大学生穿的工装裤子男士秋季新款潮牌束脚休闲长裤潮流百搭宽松直筒九分男裤 浅蓝 L"}],"score":0.5080620980859872},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/131961/32/8165/100037/5f449f88Ed8a79eb8/584dd46e5b887e06.jpg","item_name":"2020年夏季休闲长裤男潮牌薄款韩版潮流百搭工装裤直筒宽松九分夏天裤子 6602黑色 L"}],"score":0.5080620980859872},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/128142/8/10855/99671/5f44a1fdEac8d830c/e877098ff6f17178.jpg","item_name":"木子胜春秋款男士休闲裤男宽松直筒九分裤男百搭潮流束脚裤子男 蓝色 S"}],"score":0.5074144821462977},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/126743/11/10731/223936/5f44c0c3E281e44c8/6c2466c3e31678bd.jpg","item_name":"【旅游日常穿的】泰国大象裤男女情侣裤夏季灯笼裤宽松大码显瑜伽裤沙滩海边度假2020 绿色高腰(20年新款) 系带款加大(3尺5腰围内)"}],"score":0.49274136229177223},{"items":[{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/125910/33/10655/87615/5f44b5f3Ec30a1e9f/d270f89f5ac36365.jpg","item_name":"℘袋鼠/DaiShu高端轻奢国际大牌潮牌中青年休闲商务男士穿的绅士男装春季复古做旧水洗男士哈伦牛仔裤 兰白 28"},{"category_id":"2","category_name":"下衣","image_url":"https://img14.360buyimg.com/da/jfs/t1/120072/40/10756/63454/5f44809aEa8134c3c/1e5c392eccee720d.jpg","item_name":"花花公子2020春夏季新品松紧裤腰青年韩版收口小脚抽绳长裤潮流时尚百搭运动休闲裤男宽松直筒束脚裤 MF-8818咖啡色 3XL"}],"score":0.4926376151386723}],"status_code":"0","status_message":"success"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 5:53 下午
     */
    @Test
    public void recommend_outfits() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/recommend_outfits";
        String value = "image=" + imageBase64 + "&outfit_num=" + 10;
        HttpEntity requestEntity = new HttpEntity<>(value);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","successful_list":["204780573"]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:46 上午
     */
    @Test
    public void product_image_create_product() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_create_product";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("product_id", "204780573");
        map1.put("product_name", "guy's windbreaker");
        map1.put("category", "服装");
        mapList.add(map1);
        paramMap.put("product_list", mapList);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":498,"remainTimes":498,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","task_id":"3548/ErgouWarehouse/1603763201.548129"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:49 上午
     */
    @Test
    public void product_image_add() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_add";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("product_id", "204780573");
        map1.put("image_content", imageBase64);
        map1.put("image_id", "chuanda.jpg");
        map1.put("image_info", "这是个卫衣");
        mapList.add(map1);
        paramMap.put("image_list", mapList);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":496,"remainTimes":496,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","pending_list":[],"succeeded_list":[{"image_id":"chuanda.jpg","product_id":"204780573"}],"task_status":"FINISHED"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:52 上午
     */
    @Test
    public void product_image_status() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_status";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("task_id", "3548/ErgouWarehouse/1603763201.548129");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":494,"remainTimes":494,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"images":[{"image_id":"chuanda.jpg","image_info":"这是个卫衣","product_id":"204780573","product_name":"guy's windbreaker","similarity":0.9663157821214234}],"message":"SUCCESS","rectangle":[{"bottom":728.2052612304688,"left":203.5562744140625,"right":596.8760375976562,"top":138.70172119140625}]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:58 上午
     */
    @Test
    public void product_image_search() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_search";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("image_content", imageBase64);
        paramMap.put("collection_name", "ErgouWarehouse");
        paramMap.put("category", "服装");
        paramMap.put("topk", "10");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":493,"remainTimes":493,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"image_list":[{"image_id":"chuanda.jpg","image_info":"这是个卫衣"}],"message":"SUCCESS"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 9:59 上午
     */
    @Test
    public void product_image_info() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_info";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        paramMap.put("product_id", "204780573");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":492,"remainTimes":492,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","successful_list":[{"image_id":"chuanda.jpg","product_id":"204780573"}]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:02 上午
     */
    @Test
    public void product_image_del_image() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_image";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("product_id", "204780573");
        map1.put("image_id", "chuanda.jpg");
        mapList.add(map1);
        paramMap.put("image_id_list", mapList);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":491,"remainTimes":491,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"failed_list":[],"message":"SUCCESS","successful_list":["204780573"]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:04 上午
     */
    @Test
    public void product_image_del_product() {
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_product";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        List<String> ids = new ArrayList<>();
        ids.add("204780573");
        paramMap.put("product_id_list", ids);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":490,"remainTimes":490,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"info":"[ErgouWarehouse] is deleting.","message":"SUCCESS"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/27 10:05 上午
     */
    @Test
    public void product_image_del_collection() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_collection";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "ErgouWarehouse");
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"category1":"口罩","category2":"暂无","code":0,"extra_info":[],"is_medical":0,"message":"SUCCESS","spec_no":"暂无"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:02 下午
     */
    @Test
    public void medical_material_recognize() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/medical_material_recognize";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("image_content", imageBase64);
        HttpEntity requestEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":498,"remainTimes":498,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":1,"message":"ok","result":{"total":11,"list":[{"sku_count":280,"avg_price":4650,"view_count":13350000,"order_rate":0.116,"name":"对开门国产高端冰箱市场","id":0},{"sku_count":310,"avg_price":6189,"view_count":6750000,"order_rate":0.0677,"name":"十字对开门冰箱市场","id":1},{"sku_count":290,"avg_price":4730,"view_count":5990000,"order_rate":0.0604,"name":"多门国产冰箱市场","id":2},{"sku_count":190,"avg_price":2370,"view_count":5980000,"order_rate":0.1452,"name":"三门国产高端冰箱市场","id":3},{"sku_count":270,"avg_price":2900,"view_count":5270000,"order_rate":0.2087,"name":"双门冰箱市场","id":4},{"sku_count":80,"avg_price":2290,"view_count":4140000,"order_rate":0.0713,"name":"对开门国产中低端冰箱市场","id":5},{"sku_count":140,"avg_price":2450,"view_count":3520000,"order_rate":0.1145,"name":"迷你冰箱市场","id":6},{"sku_count":100,"avg_price":1200,"view_count":3520000,"order_rate":0.17,"name":"三门国产中低端冰箱市场","id":7},{"sku_count":140,"avg_price":9680,"view_count":3250000,"order_rate":0.0225,"name":"对开门进口品牌冰箱市场","id":8},{"sku_count":120,"avg_price":14000,"view_count":1250000,"order_rate":0.0053,"name":"多门进口品牌冰箱市场","id":9}],"size":10}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:16 下午
     */
    @Test
    public void marketSegmentationAnalyse() {
        String requestUrl = gatewayUrl + "/neuhub/marketSegmentationAnalyse?page_size=10&page_now=1&category=1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":1,"message":"ok","result":{"total":235,"list":[{"brandname_full":"海尔（Haier）","brand_recognition_score":20.0,"brand_reputation_score":5.0,"brand_marketvalue_score":35.0,"brand_search_score":10.0,"brand_sale_score":25.0,"brand_loyalty_score":21.721543876823016,"brand_compete_score":117.72154387682302,"id":0},{"brandname_full":"美的（Midea）","brand_recognition_score":20.0,"brand_reputation_score":3.858269194668669,"brand_marketvalue_score":20.970974324763585,"brand_search_score":5.922975409687451,"brand_sale_score":16.060292296883617,"brand_loyalty_score":24.076805323027614,"brand_compete_score":91.60584621495991,"id":1},{"brandname_full":"TCL","brand_recognition_score":19.977876106194692,"brand_reputation_score":1.058809419519012,"brand_marketvalue_score":3.8484068559662656,"brand_search_score":1.39995824736877,"brand_sale_score":5.151746569047768,"brand_loyalty_score":2.8665321158908568,"brand_compete_score":34.52854271898263,"id":2},{"brandname_full":"奥克斯（AUX）","brand_recognition_score":19.95575221238938,"brand_reputation_score":0.0,"brand_marketvalue_score":0.9482510403561961,"brand_search_score":1.414496378348495,"brand_sale_score":2.60927544372156,"brand_loyalty_score":3.2580253273838147,"brand_compete_score":28.18580040219945,"id":3},{"brandname_full":"容声（Ronshen）","brand_recognition_score":19.95575221238938,"brand_reputation_score":2.4099451431908725,"brand_marketvalue_score":12.704741041080455,"brand_search_score":3.20343785183327,"brand_sale_score":9.473874006682298,"brand_loyalty_score":8.155583026023487,"brand_compete_score":56.36903040418427,"id":4},{"brandname_full":"创维（Skyworth）","brand_recognition_score":19.911504424778762,"brand_reputation_score":0.9310796153765931,"brand_marketvalue_score":2.3650152683625403,"brand_search_score":0.6387727471684577,"brand_sale_score":3.1320938768617275,"brand_loyalty_score":1.177599781084472,"brand_compete_score":28.343930609828146,"id":5},{"brandname_full":"海信（Hisense）","brand_recognition_score":19.911504424778762,"brand_reputation_score":0.8474490540850593,"brand_marketvalue_score":2.3544616449662543,"brand_search_score":1.068538029978512,"brand_sale_score":2.5333158547360624,"brand_loyalty_score":4.501072480334274,"brand_compete_score":31.377422740854897,"id":6},{"brandname_full":"云米（VIOMI）","brand_recognition_score":19.911504424778762,"brand_reputation_score":0.5095008656112466,"brand_marketvalue_score":2.452144062271606,"brand_search_score":0.8873484098301427,"brand_sale_score":1.611012729208322,"brand_loyalty_score":1.629540473092848,"brand_compete_score":27.114919654855107,"id":7},{"brandname_full":"奥马（Homa）","brand_recognition_score":19.88938053097345,"brand_reputation_score":0.4948219760966147,"brand_marketvalue_score":1.1511293654745172,"brand_search_score":0.402858600657901,"brand_sale_score":1.68845348935936,"brand_loyalty_score":0.8503687162187576,"brand_compete_score":24.573888008109297,"id":8},{"brandname_full":"美菱（MeiLing）","brand_recognition_score":19.88938053097345,"brand_reputation_score":1.7792847756711094,"brand_marketvalue_score":7.780770796902353,"brand_search_score":1.3613560140127403,"brand_sale_score":5.908018570854097,"brand_loyalty_score":3.7011947987721934,"brand_compete_score":40.71814016944962,"id":9}],"size":10}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:22 下午
     */
    @Test
    public void brandCompeteAnalyse() {
        String requestUrl = gatewayUrl + "/neuhub/brandCompeteAnalyse?page_size=10&page_now=1&category=1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":494,"remainTimes":494,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"message":"ok","result":{"after_change_uv_socre":93.46,"before_change_dict":{"90度开门":"不具有","AC+净味":"不具有","DEO净味":"不具有","LTC蓝晶净味":"不具有","PST杀菌":"不具有","TABT除菌":"不具有","一体成型":"具有","一体照明":"不具有","三循环":"不具有","价格":1150,"体积":"196L","假日功能":"不具有","光合保鲜":"不具有","光触媒抗菌":"不具有","全冷气存鲜":"不具有","全开式抽屉":"不具有","全环绕气流":"不具有","净离子杀菌":"不具有","制冰":"不具有","制冷类型":"混冷","动力气流":"不具有","压缩机":"定频","双变频":"不具有","双循环":"具有","发酵功能":"不具有","复古":"不具有","多路送风":"不具有","多重杀菌":"不具有","定制格局":"不具有","宽幅变温":"不具有","宽度":"61-65cm","嵌入式":"不具有","干湿分储":"不具有","底部散热":"不具有","微冻保鲜":"不具有","微晶保鲜":"不具有","急速净味":"不具有","抗菌门封":"不具有","故障智能检测":"不具有","时尚吧台":"不具有","显示器":"具有","智能WI-FI":"不具有","智能保鲜":"不具有","智能变频":"不具有","智能杀菌":"不具有","水分子激活保鲜":"不具有","泡菜发酵":"不具有","涂层":"不具有","涡流动态杀菌":"不具有","深度":"61-65cm","温控方式":"电脑控温","温湿精控":"不具有","独立变温室":"不具有","环保内胆":"不具有","生物保鲜":"不具有","真空仓":"不具有","矢量变频":"不具有","立体蒸发器":"不具有","立体除菌":"不具有","简约风格":"不具有","精细分储":"不具有","纤细边框":"不具有","纤薄设计":"不具有","纳米水离子除菌净味":"不具有","纳米除臭":"不具有","线性变频":"不具有","细胞级养鲜":"不具有","维他养鲜":"不具有","能效等级":"二级","脱氧触媒净味":"不具有","自动低温补偿":"不具有","自动悬停门":"不具有","臻材室":"不具有","蝶门美食窗":"不具有","负氧离子养鲜":"不具有","贴合橱柜":"不具有","速食盘设计":"不具有","金属酒架":"不具有","银离子除菌净味":"不具有","长效净味":"不具有","门中门":"不具有","门的款式":"三门","防串味":"具有","防倾倒设计":"不具有","隐形门把手":"不具有","零度保鲜":"不具有","面板材质":"彩钢","颜色":"金色","食物过期提醒":"不具有","饮水功能":"不具有","高度":"160.1-170cm"},"before_change_uv_socre":87.85,"changed_dict":{"价格":[1150,900],"显示器":["具有","不具有"],"智能杀菌":["不具有","具有"],"温控方式":["电脑控温","机械控温"]}},"used_time":649}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 2:42 下午
     */
    @Test
    public void productUpgrade() {
        String requestUrl = gatewayUrl + "/neuhub/productUpgrade";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> map = new HashMap<>();
        map.put("sku", "100003831465");
        String body = JSON.toJSONString(map);
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":483,"remainTimes":483,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"message":"ok","result":{"design_dict":{"90度开门":"不具有","AC+净味":"不具有","DEO净味":"不具有","LTC蓝晶净味":"不具有","PST杀菌":"不具有","TABT除菌":"不具有","一体成型":"不具有","一体照明":"不具有","三循环":"不具有","价格":1040,"体积":"175L","假日功能":"具有","光合保鲜":"不具有","光触媒抗菌":"不具有","全冷气存鲜":"不具有","全开式抽屉":"不具有","全环绕气流":"不具有","净离子杀菌":"不具有","制冰":"不具有","制冷类型":"风冷","动力气流":"不具有","压缩机":"定频","双变频":"不具有","双循环":"不具有","发酵功能":"不具有","复古":"不具有","多路送风":"不具有","多重杀菌":"不具有","定制格局":"不具有","宽幅变温":"不具有","宽度":"60cm及以下","嵌入式":"不具有","干湿分储":"不具有","底部散热":"不具有","微冻保鲜":"不具有","微晶保鲜":"不具有","急速净味":"不具有","抗菌门封":"不具有","故障智能检测":"不具有","时尚吧台":"不具有","显示器":"不具有","智能WI-FI":"具有","智能保鲜":"不具有","智能变频":"不具有","智能杀菌":"不具有","水分子激活保鲜":"不具有","泡菜发酵":"不具有","涂层":"不具有","涡流动态杀菌":"不具有","深度":"51-55cm","温控方式":"机械控温","温湿精控":"不具有","独立变温室":"不具有","环保内胆":"具有","生物保鲜":"不具有","真空仓":"不具有","矢量变频":"不具有","立体蒸发器":"不具有","立体除菌":"不具有","简约风格":"具有","精细分储":"不具有","纤细边框":"不具有","纤薄设计":"不具有","纳米水离子除菌净味":"不具有","纳米除臭":"不具有","线性变频":"不具有","细胞级养鲜":"不具有","维他养鲜":"不具有","能效等级":"二级","脱氧触媒净味":"不具有","自动低温补偿":"不具有","自动悬停门":"不具有","臻材室":"不具有","蝶门美食窗":"不具有","负氧离子养鲜":"不具有","贴合橱柜":"不具有","速食盘设计":"不具有","金属酒架":"不具有","银离子除菌净味":"不具有","长效净味":"不具有","门中门":"不具有","门的款式":"双门","防串味":"不具有","防倾倒设计":"不具有","隐形门把手":"不具有","零度保鲜":"不具有","面板材质":"钢化玻璃","颜色":"银色","食物过期提醒":"不具有","饮水功能":"不具有","高度":"140.1-150cm"},"uv_score":92.13},"used_time":594}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:11 下午
     */
    @Test
    public void productCustom() {
        String requestUrl = gatewayUrl + "/neuhub/productCustom";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> map = new HashMap<>();
        map.put("brand", "康佳");
        map.put("doorType", "双门");
        HttpEntity httpEntity = new HttpEntity(map, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":1000,"message":"success","res":{"analysis_list":[{"appear":41.94054615017829,"basic_fun":58.478470618911615,"brand":33.006242634562966,"chara_fun":30.0,"item_sku_id":1293744,"price":83.41870976674363},{"appear":59.841843453859354,"basic_fun":33.41643482827408,"brand":33.006242634562966,"chara_fun":65.0,"item_sku_id":100004013943,"price":51.994991132020004},{"appear":59.841843453859354,"basic_fun":30.0,"brand":33.006242634562966,"chara_fun":65.0,"item_sku_id":100007119438,"price":30.0},{"appear":62.42162044101015,"basic_fun":99.1359981418841,"brand":100.0,"chara_fun":82.5,"item_sku_id":4717300,"price":66.31667447929976},{"appear":99.99999999999999,"basic_fun":100.0,"brand":30.0,"chara_fun":100.0,"item_sku_id":100002796189,"price":100.0},{"appear":30.0,"basic_fun":58.478470618911615,"brand":33.006242634562966,"chara_fun":30.0,"item_sku_id":2312215,"price":72.69486254950516}],"sku_list":{"input_sku_id":"2312215","sku_compet_list":["1293744","100004013943","100007119438","4717300","100002796189"]}}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:28 下午
     */
    @Test
    public void compete_analysis() {
        String requestUrl = gatewayUrl + "/neuhub/compete_analysis";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(3);
        params.add("sku_id", "2312215");
        HttpEntity httpEntity = new HttpEntity(params, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"text":"How do you like it?","code":"12000"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:31 下午
     */
    @Test
    public void nmt() {
        String requestUrl = gatewayUrl + "/neuhub/nmt?type=1&query=你好";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":497,"remainTimes":497,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":"1000","msg":"Request OK!","result":{"score":0.52870667,"words":{"word1":"电脑","word2":"平板"}}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:37 下午
     */
    @Test
    public void similar() {
        String requestUrl = gatewayUrl + "/neuhub/similar";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, String> params = new HashMap<>();
        params.put("word1", "电脑");
        params.put("word2", "平板");
        Object body;
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":"1000","msg":"Request OK!","result":{"word":"快递","dimension":300,"vector":[-0.037911,0.167084,0.138183,0.116943,0.069095,0.212419,-0.116308,0.058488,-0.275309,-0.329532,-0.193921,0.142162,-1.230227,-0.293185,0.273049,0.171599,-0.151035,-0.05102,0.018167,0.084426,0.150819,-0.034304,0.319141,-0.291756,0.265164,-0.012452,0.36238,0.529566,0.359141,-0.033559,0.025293,-0.241511,-0.15321,0.291486,-0.228813,0.204874,-0.388871,-1.084903,0.06182,0.004443,-0.218892,-0.379701,-0.006696,0.065591,0.0166,-0.306468,-0.040063,-0.21588,-0.03083,0.355667,-0.120361,0.819872,-0.180082,0.406822,0.07096,-0.029594,0.195672,0.274622,0.239646,0.057883,-0.038688,0.401486,0.963427,-0.571029,-0.120841,0.51343,-0.177278,0.185079,-0.579952,0.099199,-0.220018,0.300598,-0.325692,0.598343,0.553905,-0.214731,0.036638,0.02326,0.9815,-0.353119,-0.439797,-0.129576,0.189542,0.242864,0.001991,-0.66613,-0.168538,-0.597678,-0.112697,0.01833,-0.058606,0.203445,0.239567,0.072739,0.053458,0.228007,0.360398,-0.190158,-0.372746,0.204533,0.477332,-0.442126,-0.089082,0.179749,0.194156,-0.136108,-0.591195,0.205773,0.352406,0.340998,0.77172,0.250099,-0.044077,0.149916,-0.35116,-0.111114,-0.700207,0.976227,0.267899,-0.134144,-0.172125,-0.251711,0.078909,-0.30218,-0.145877,-0.05497,0.602489,0.51856,0.181968,-0.599109,0.094678,-0.186928,0.374335,0.262312,-0.035161,0.110218,-0.125815,-0.663861,-0.180383,-0.002755,0.18194,-0.533794,-0.063088,0.42061,-0.091996,0.218498,-0.029708,0.136975,-0.852604,-0.342475,-0.043089,-0.377562,0.015,-0.330567,-0.137931,-0.04559,-0.050755,-0.081761,0.106078,-0.165312,0.406102,0.199186,0.125872,0.018433,0.020505,0.012398,0.058236,-0.303899,-0.708292,-0.188794,-0.134696,0.746445,0.53082,-0.15328,-0.074665,0.326486,0.961386,0.172559,-0.391814,-0.561391,0.111984,0.531029,-0.272769,-0.297516,-0.040657,0.224285,0.137471,0.189438,-0.110474,0.060339,0.124136,0.674977,-0.246781,-0.479907,-0.280123,-0.027731,0.177524,-0.077416,0.463686,0.127706,0.283156,-0.431895,-0.374185,0.37579,-0.203099,0.309054,-0.722817,-0.86738,-0.521901,0.070132,0.148766,0.345367,-0.111663,0.234061,-0.010744,0.008113,-0.083656,-0.574575,-0.311329,0.587948,0.606915,0.441596,-0.107999,-0.201121,0.486181,-0.579378,0.210087,-0.991549,0.071253,0.653587,0.269964,0.105729,0.056637,-0.234406,0.142797,-0.441458,-0.836428,0.177324,-0.058937,-0.147898,0.641219,0.284513,-0.05259,-0.226084,0.811699,0.08592,0.195313,0.109894,0.072802,-0.067763,-0.035919,0.693025,-0.136339,-0.128742,0.524283,-0.207525,-0.536324,-0.085432,-0.04106,0.015434,-0.227281,-0.473043,-0.249679,0.045114,0.101295,0.199026,-0.071585,-0.401166,0.242846,0.372944,0.404762,0.389531,-0.105451,-0.012071,-0.051103,-0.283482,0.444593,0.270422,0.472096,-0.074616,-0.445827,0.275619,-0.576021,-0.767331,-0.456695,-0.206172,-0.024667,-0.529994,0.24623,-0.165747,1.031492,-0.006329,0.014437,0.079204,-0.349535,-0.065707,0.164571,-0.584908,-0.036687,-0.07459]}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 3:39 下午
     */
    @Test
    public void word_vector() {
        String requestUrl = gatewayUrl + "/neuhub/word_vector";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, String> params = new HashMap<>();
        params.put("word", "快递");
        Object body;
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void phrase_extract() {
        String requestUrl = gatewayUrl + "/fagougou/phrase_extract?document=算法工程师的算法能力特别重要";
        // String requestUrl = gatewayUrl + "/neuhub/phrase_extract?document=算法工程师的算法能力特别重要";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":49,"remainTimes":49,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":{"title":"展印 天然绿檀木 梳子","content":"天然绿檀木，梳宽齿乳腺。这款梳子的梳齿采用的是优质的原料制作而成，可以很好的按摩头皮，促进头部血液循环，让你的头发更加健康。","contentList":[{"content":"天然绿檀木，梳宽齿乳腺。这款梳子的梳齿采用的是优质的原料制作而成，可以很好的按摩头皮，促进头部血液循环，让你的头发更加健康。","score":1.0},{"content":"梳子采用传统手工打磨制作而成，梳齿间距合理，可以按摩头部穴位，促进头皮血液循环，让头发更加柔顺。天然的绿檀木梳，能更好的呵护你的秀发，同时还能放松身心，舒缓压力。","score":1.0},{"content":"这是一款很好的按摩梳，梳子使用了天然的梳宽齿檀木制作而成，不含有毒有害的物质，可以促进血液循环，让你的头发更加健康。而且它的手柄是乳腺的，很有弹性，也是很适合用来送朋友。","score":1.0}]},"version":"1.0.8.O"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:07 下午
     */
    @Test
    public void create_article() {
        String requestUrl = gatewayUrl + "/neuhub/create_article?sku=52119743815&numContent=3";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"best_answer":"又见面啦！"}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:10 下午
     */
    @Test
    public void chatbot() {
        String requestUrl = gatewayUrl + "/neuhub/chatbot?context=你好";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":[{"ppl":105.49150085449219,"items":[{"word":"这","prob":0.000046},{"word":"是","prob":0.000371},{"word":"一","prob":0.021668},{"word":"款","prob":0.011721},{"word":"无","prob":0.011929},{"word":"袖","prob":0.001146},{"word":"连","prob":0.012524},{"word":"衣","prob":0.191675},{"word":"裙","prob":0.616922},{"word":"，","prob":0.006174},{"word":"蝴","prob":0.0000040},{"word":"蝶","prob":0.984052},{"word":"结","prob":0.210745},{"word":"装","prob":0.016445},{"word":"饰","prob":0.117097},{"word":"甜","prob":0.004208},{"word":"美","prob":0.523187},{"word":"大","prob":0.001476},{"word":"方","prob":0.021803},{"word":"。","prob":0.000563}]}],"msg":""}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:12 下午
     */
    @Test
    public void language_smooth() {
        String requestUrl = gatewayUrl + "/neuhub/language_smooth?sentence=这是一款无袖连衣裙，蝴蝶结装饰甜美大方。";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":{"synonym":["智慧果","平安果","超凡子","平波","滔婆","天然子","苹果公司","小米","黑莓","苹果电脑","iPad","新产品","产品","洋葱","草莓","苹果新","苹果iphone","苹果的iphone","iphone产品"],"inputText":"苹果"}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:14 下午
     */
    @Test
    public void corpus_synonyms() {
        String requestUrl = gatewayUrl + "/neuhub/corpus_synonyms?word=苹果";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":996,"remainTimes":996,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":{"ss":0.041114978227711665,"results":{"0":["算法对噪音和异常点比较的敏感","算法的可解释度比较强","对于不是凸的数据集比较难收敛","调参相对于传统聚类算法稍复杂"],"1":["现代足球运动起源于英国","美国职业篮球联赛有大约30个球队","休斯顿火箭队总经理发表涉华不正当言论","足球和篮球是世界上参与人数最多的两项运动"]}}}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:23 下午
     */
    @Test
    public void corpus_aggregation() {
        String requestUrl = gatewayUrl + "/neuhub/corpus_aggregation";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> params = new HashMap<>();
        params.put("checkAlgo", "kmeans");
        params.put("inputText", Arrays.asList("算法对噪音和异常点比较的敏感", "算法的可解释度比较强", "对于不是凸的数据集比较难收敛", "调参相对于传统聚类算法稍复杂", "现代足球运动起源于英国", "美国职业篮球联赛有大约30个球队", "休斯顿火箭队总经理发表涉华不正当言论", "足球和篮球是世界上参与人数最多的两项运动").toArray());
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":[["幂","颖","洋"]],"msg":""}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:25 下午
     */
    @Test
    public void language_cloze() {
        String requestUrl = gatewayUrl + "/neuhub/language_cloze?sentence=这是杨*同款包包，精选优质皮料制作";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
     * 调用结果: {"code":"10000","charge":true,"remain":999,"remainTimes":999,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":200,"data":["解放军","移交"]}}
     *
     * @return void
     * @author chenchen
     * @date 2020/10/26 4:27 下午
     */
    @Test
    public void hotwords() {
        String requestUrl = gatewayUrl + "/neuhub/hotwords";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        Map<String, Object> params = new HashMap<>();
        params.put("input_text", "据港媒报道，中区军用码头今早时起正式移交驻港解放军，划为军事禁区。驻军进一步采纳特区政府建议，除面向维港的一面外，军用码头其余三面采用活动栏栅，可以打开供市民通行");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    /**
         * 驾驶证识别	/neuhub/ocr_driver_license
         * <200,{"code":"10000","charge":true,"remain":495,"remainTimes":495,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"message":"success","request_id":"bece14ee-14f5-11eb-a58e-fa163ea0a74a","resultData":{"address":"重庆市丰都县三合镇平中路二支路24号3单元7-3","birthday":"1990-03-07","class":"C1","country":"中国","file_id":"","id":"50023019900307002X","issue_date":"2012-02-21","name":"王欢","record":"","sex":"女","valid_for":"6年","valid_from":"2012-02-21"}}},{Date=[Fri, 23 Oct 2020 06:05:27 GMT], Content-Type=[application/json;charset=utf-8], Content-Length=[494], Connection=[keep-alive], Server=[nginx/1.16.1], Vary=[Accept-Encoding]}>
         */
        @Test
        public void ocr_driver_license() {
            byte[] data = dataBinary(picture);
            HttpEntity<Object> requestEntity = new HttpEntity<>(data);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/ocr_driver_license";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"message":"success","request_id":"c2b21a148bc6ee541cadbfa1bcb999cd","resultData":{"owner":"李丽","code":"903","address":"北京市朝围区来广营西路8号国创产业国监号楼二层东责区","money":"1463.4万元","registration_code":"","composition":"","business_type":"股份有限公司(非上市、自然人投资或控股)","name":"汉唐信通(北京咨询股份有限公司","valid_time":"2006年05月22日至2056年05月21日","registration_time":"2006年05月22日"},"code":0}}
         */
        // 营业执照识别	/neuhub/ocr_business
        @Test
        public void ocr_business() {
            byte[] data = dataBinary(picture);
            HttpEntity<Object> requestEntity = new HttpEntity<>(data);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/ocr_business";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":"0","message":"success","request_id":"f7d18c9c2d814cf6b9a72d46411b5870","result":{"bank_date":"","bank_name":"","bank_number":"5550472687299","card_count":"","card_name":"","card_type":""}}}
         */
        // 银行卡识别	/neuhub/ocr_bankcard
        @Test
        public void ocr_bankcard() {
            byte[] data = dataBinary(picture);
            HttpEntity<Object> requestEntity = new HttpEntity<>(data);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/ocr_bankcard";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * < 200, {
         * "code": "10000",
         * "charge": true,
         * "remain": 498,
         * "remainTimes": 498,
         * "remainSeconds": -1,
         * "msg": "查询成功,扣费",
         * "result": {
         * "request_id": "92f9d2ca-14f7-11eb-b4b6-fa163ecc10c6",
         * "status": 0,
         * "message": "OK",
         * "taskid": "c82e5ba7-67cb-51ef-bb47-f56023957591"
         * }
         * }, {
         * Date = [Fri, 23 Oct 2020 06: 18: 32 GMT],
         * Content - Type = [application / json;
         * charset = utf - 8],
         * Content - Length = [245],
         * Connection = [keep - alive],
         * Server = [nginx / 1.16.1]
         * }
         * >
         */
        // 录音文件识别_创建任务接口说明	/neuhub/fasr_create
        @Test
        public void fasr_create() {
            //body请求参数
            Map<String, Object> postParameters = new HashMap<>();
            //语音识别模型， 可选项：
            // - general，通用场景，需要使用 16000 Hz 采样率的音频
            postParameters.put("domain", "general");
            //音频格式， 可选项：
            // - wav
            // - mp3
            // - amr
            postParameters.put("format", "mp3");
            //samplerate 音频采样率， 采样率需与所选模型匹配
            // - 16000
            postParameters.put("samplerate", 16000);
            postParameters.put("speech_url", "http://voice.s3.cn-north-1.jdcloud-oss.com/tmp_audio/audio-00000.mp3");
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(postParameters);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/fasr_create";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * < 200, {
         * "code": "10000",
         * "charge": true,
         * "remain": 495,
         * "remainTimes": 495,
         * "remainSeconds": -1,
         * "msg": "查询成功,扣费",
         * "result": {
         * "request_id": "454a3302-14f8-11eb-a506-fa163ecc10c6",
         * "status": 0,
         * "message": "OK",
         * "content": {
         * "taskid": "c82e5ba7-67cb-51ef-bb47-f56023957591",
         * "task_status": "SUCCESS",
         * "sentences": [{
         * "text": "如果肤色粉红脸上绒毛细腻，柔软，皮肤粉嫩光滑，眼睛明亮，有神身材圆润。",
         * "begin_time": 520,
         * "end_time": 9938
         * }
         * ],
         * "task_result": "如果肤色粉红脸上绒毛细腻，柔软，皮肤粉嫩光滑，眼睛明亮，有神身材圆润。"
         * }
         * }
         * }, {
         * Date = [Fri, 23 Oct 2020 06: 23: 31 GMT],
         * Content - Type = [application / json;
         * charset = utf - 8],
         * Content - Length = [567],
         * Connection = [keep - alive],
         * Server = [nginx / 1.16.1],
         * Vary = [Accept - Encoding]
         * }
         * >
         */
        // 录音文件识别_查询接口说明	/neuhub/fasr_query
        @Test
        public void fasr_query() {
            //body请求参数
            Map<String, Object> postParameters = new HashMap<>();
            postParameters.put("taskid", "c82e5ba7-67cb-51ef-bb47-f56023957591");
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(postParameters);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/fasr_query";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":496,"remainTimes":496,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"request_id":"f9f99b27-1ae2-40b2-a758-23d63b574b55","status":61003,"index":-1,"message":"request params error.","content":[{"text":""}]}}
         */
        // 声纹识别	/neuhub/vpr
        @Test
        public void vpr() {
            //header示例参数
            HashMap<Object, Object> encodeMap = new HashMap<>();
            encodeMap.put("channel", 1);
            encodeMap.put("format", "mp3");
            encodeMap.put("sample_rate", 16000);

            HashMap<Object, Object> property = new HashMap<>();
            property.put("platform", "Linux");
            property.put("version", "0.0.0.1");
            property.put("vpr_mode", "enroll");
            property.put("autoend", false);
            // property.put("encode", JSON.toJSONString(encodeMap));
            property.put("encode", encodeMap);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/octet-stream");
            // httpHeaders.set("Content-Type", "application/json");
            httpHeaders.set("Application-Id", "search-app");
            httpHeaders.set("Request-Id", UUID.randomUUID().toString());
            httpHeaders.set("User-Id", "IMEI-12345678");
            httpHeaders.set("Sequence-Id", "-1");
            httpHeaders.set("Server-Protocol", "1");
            httpHeaders.set("Net-State", "2");
            httpHeaders.set("Applicator", "1");
            httpHeaders.set("Property", JSON.toJSONString(property));
            byte[] data = dataBinary(picture);
            HttpEntity<Object> requestEntity = new HttpEntity<>(data, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/vpr";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {
         *     "code": "10000",
         *     "charge": true,
         *     "remain": 998,
         *     "remainTimes": 998,
         *     "remainSeconds": -1,
         *     "msg": "查询成功,扣费",
         *     "result": {
         *         "request_id": "94deb455-cede-49b3-b546-df1907a55987",
         *         "index": -1,
         *         "err_no": 0,
         *         "err_msg": "",
         *         "content": [{
         *                 "text": ""
         *             }
         *         ]
         *     }
         * }
         */
        // TODO 实时语音识别	/neuhub/rt_asr
        @Test
        public void rt_asr() {
            //header示例参数
            HashMap<Object, Object> encodeMap = new HashMap<>();
            encodeMap.put("channel", 1);
            encodeMap.put("format", "wav");
            encodeMap.put("sample_rate", 16000);

            HashMap<Object, Object> property = new HashMap<>();
            property.put("platform", "Linux&Centos&7.3");
            property.put("version", "0.0.0.1");
            property.put("vpr_mode", "enroll");
            property.put("autoend", false);
            property.put("encode", encodeMap);

            AsrEncode asrEncode = new AsrEncode(1, "wav", 16000);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/octet-stream");
            httpHeaders.set("Application-Id", "123456789");
            httpHeaders.set("Request-Id", UUID.randomUUID().toString());
            httpHeaders.set("User-Id", "IMEI-12345678");
            httpHeaders.set("Sequence-Id", "-1");
            httpHeaders.set("Server-Protocol", "1");
            httpHeaders.set("Net-State", "2");
            httpHeaders.set("Applicator", "1");
            httpHeaders.set("Property", JSON.toJSONString(property));
            byte[] data = dataBinary(picture);
            HttpEntity<Object> requestEntity = new HttpEntity<>(data, httpHeaders);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/rt_asr";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果:{
         *     "code": "10000",
         *     "charge": true,
         *     "remain": 495,
         *     "remainTimes": 495,
         *     "remainSeconds": -1,
         *     "msg": "查询成功,扣费",
         *     "result": {
         *         "status": 13004,
         *         "message": "no face detected",
         *         "request_id": "d41d8cd98f00b204e9800998ecf8427e"
         *     }
         * }
         */

        // 人脸口罩识别	/neuhub/mask_detect
        @Test
        public void mask_detect() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            byte[] data = dataBinary(picture);
            String imageBase64 = imageBase64(data);
            Map<String, Object> map = new HashMap<>();
            map.put("imageUrl", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603452217976&di=9fce40e49bb9f6292b72404e02b1aa1c&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201809%2F18%2F122912icv5h754sr0sa4su.jpg");
            // map.put("imageBase64Str", imageBase64);
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/mask_detect";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"code":0,"faceItemsA":[{"boundingBox":{"height":394.12005615234375,"left":110.85391998291016,"top":87.896392822265625,"width":281.47579956054688},"isValidPosture":true,"posture":{"pitch":-2.1791214942932129,"roll":0.089315876364707947,"yaw":1.458629846572876},"ptScore":3.4512131214141846}],"faceItemsB":[{"boundingBox":{"height":394.12005615234375,"left":110.85391998291016,"top":87.896392822265625,"width":281.47579956054688},"isValidPosture":true,"posture":{"pitch":-2.1791214942932129,"roll":0.089315876364707947,"yaw":1.458629846572876},"ptScore":3.4512131214141846}],"msg":"Accept","processTimeInMs":65,"score":"1.00","timestamp":1603442688456}}
         */
        // 人脸对比	/neuhub/faceCompareV1
        @Test
        public void faceCompareV1() {
            byte[] data = dataBinary(picture);
            String imageBase64 = imageBase64(data);
            Map<String, Object> postParameters = new HashMap<>();
            postParameters.put("imgBase64A", imageBase64);
            postParameters.put("imgBase64B", imageBase64);
            HttpEntity<Object> requestEntity = new HttpEntity<>(postParameters);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/faceCompareV1";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":497,"remainTimes":497,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"humanDetectionResult":[[26,19,492,493,0.9443610310554504]],"request_id":"1603442912.3342998","status":0,"personreidResult":[[0.06158406659960747,0.1802629679441452,0.3367677330970764,0.10755850374698639,0.1648513823747635,0.15995091199874878,0.18619728088378906,0.15592257678508759,0.1317620575428009,0.11277367919683456,0.09785978496074677,0.0487479642033577,0.14050057530403137,0.2729910612106323,0.045049842447042465,0.22330348193645477,0.20347793400287628,0.04465861991047859,0.09124334901571274,0.06369724124670029,0.08483007550239563,0.08226829022169113,0.15183323621749878,0.10801873356103897,0.40870198607444763,0.18734504282474518,0.07646587491035461,0.09170222282409668,0.0968693420290947,0.042073555290699005,0.0852104052901268,0.13055087625980377,0.0839637741446495,0.13347306847572327,0.2916603982448578,0.16703949868679047,0.08586893230676651,0.13584645092487335,0.16489841043949127,0.2132907658815384,0.09318462759256363,0.2854439318180084,0.28199514746665955,0.08014347404241562,0.04059407487511635,0.1650361716747284,0.11592138558626175,0.12639962136745453,0.1978340595960617,0.16922341287136078,0.09050201624631882,0.16104382276535034,0.13240841031074524,0.18162858486175537,0.11318673938512802,0.1153986006975174,0.19769082963466644,0.2529394328594208,0.23615972697734833,0.10934874415397644,0.12222719192504883,0.06782732903957367,0.2283334881067276,0.09160634130239487,0.08388607949018478,0.5173776149749756,0.3428187966346741,0.08978289365768433,0.061790671199560165,0.12686297297477722,0.2212381809949875,0.12648825347423553,0.20644015073776245,0.1255718171596527,0.11211121827363968,0.10566899925470352,0.08083371818065643,0.1522406041622162,0.0839107558131218,0.09513028711080551,0.10558118671178818,0.11381512880325317,0.28331366181373596,0.10312607139348984,0.07983271777629852,0.2181142419576645,0.14412005245685577,0.2721022963523865,0.16348294913768768,0.11704599112272263,0.2427740842103958,0.07802591472864151,0.15039649605751038,0.09622412174940109,0.0783056765794754,0.4355747699737549,0.28232666850090027,0.24886855483055115,0.09794750809669495,0.129888117313385,0.10884477943181992,0.13981617987155914,0.05712912231683731,0.16617317497730255,0.1253289431333542,0.1525615155696869,0.10862874239683151,0.07372624427080154,0.15065176784992218,0.17383448779582977,0.15256312489509583,0.16320271790027618,0.0636860802769661,0.23294596374034882,0.19603854417800903,0.1360875517129898,0.05614813044667244,0.18621943891048431,0.0713321790099144,0.24210360646247864,0.05854178965091705,0.1023549810051918,0.13580381870269775,0.15528899431228638,0.14281554520130157,0.08726087212562561,0.1651354879140854,0.29190394282341003,0.0802324041724205,0.4596564471721649,0.13710764050483704,0.1176513135433197,0.11997304111719131,0.17459100484848022,0.4170601963996887,0.3222144842147827,0.09583863615989685,0.16337299346923828,0.2065599262714386,0.3087543249130249,0.11167941987514496,0.0524267852306366,0.04448915645480156,0.0729358121752739,0.07620454579591751,0.08860349655151367,0.1171119436621666,0.09136608242988586,0.07908487319946289,0.1138167753815651,0.19576340913772583,0.10308927297592163,0.2323942333459854,0.05982869490981102,0.05829353630542755,0.25248274207115173,0.5275042653083801,0.21439452469348907,0.2002701312303543,0.1667177975177765,0.04585607722401619,0.0625954121351242,0.11736470460891724,0.17299017310142517,0.10449530929327011,0.04705101251602173,0.15375754237174988,0.14081484079360962,0.07773996889591217,0.15033340454101562,0.36184656620025635,0.10411613434553146,0.07742480933666229,0.10250627249479294,0.14313551783561707,0.06316333264112473,0.16607795655727386,0.13104218244552612,0.15110759437084198,0.12714378535747528,0.08956881612539291,0.1883930265903473,0.19810019433498383,0.05560728535056114,0.04562072455883026,0.23230011761188507,0.10672340542078018,0.1460833102464676,0.23620820045471191,0.1658916175365448,0.052329182624816895,0.031509339809417725,0.11187056452035904,0.1202884316444397,0.11105172336101532,0.0996169000864029,0.08379924297332764,0.12532126903533936,0.23856493830680847,0.0513945035636425,0.20068983733654022,0.0601307637989521,0.16619819402694702,0.13179107010364532,0.0475921556353569,0.1866193562746048,0.0761861577630043,0.1641785204410553,0.11935190856456757,0.11369289457798004,0.2812935709953308,0.06617913395166397,0.21795526146888733,0.10361437499523163,0.10110370814800262,0.12153417617082596,0.23463279008865356,0.2647269666194916,0.20166194438934326,0.11748652160167694,0.060499176383018494,0.2059054672718048,0.16268903017044067,0.34941065311431885,0.13649699091911316,0.11895795911550522,0.11734026670455933,0.1870080977678299,0.12250220030546188,0.14861994981765747,0.05343560874462128,0.1492287963628769,0.07584188878536224,0.10895557701587677,0.08845101296901703,0.08403938263654709,0.21400387585163116,0.17976148426532745,0.09616902470588684,0.37481847405433655,0.2943841218948364,0.08661901950836182,0.1611027717590332,0.2919732630252838,0.09989999979734421,0.10058336704969406,0.1944676637649536,0.3631145656108856,0.1475994884967804,0.13011431694030762,0.24244964122772217,0.08227270096540451,0.34044352173805237,0.15478825569152832,0.09539244323968887,0.041496939957141876,0.11962898075580597,0.12583452463150024,0.12051887065172195,0.0818994864821434,0.20747114717960358,0.5192723870277405,0.16753508150577545,0.0672667846083641,0.09754443913698196,0.2572658061981201,0.19236363470554352,0.1993194967508316,0.1652926504611969,0.14399945735931396,0.09296518564224243,0.14113038778305054,0.16226516664028168,0.09897511452436447,0.1880020797252655,0.019032379612326622,0.06973467022180557,0.06965433806180954,0.23009605705738068,0.2243192493915558,0.15695685148239136,0.20045824348926544,0.10799230635166168,0.06460121273994446,0.1232389584183693,0.2137865573167801,0.12292831391096115,0.18688653409481049,0.13080379366874695,0.22476692497730255,0.16803573071956635,0.18669813871383667,0.18631380796432495,0.16470661759376526,0.06717317551374435,0.15454435348510742,0.16214628517627716,0.1830708384513855,0.10646001994609833,0.04946894943714142,0.20077291131019592,0.21273957192897797,0.1329510509967804,0.10727915167808533,0.11052368581295013,0.23054920136928558,0.13915477693080902,0.18345661461353302,0.1668483167886734,0.07131320983171463,0.13690701127052307,0.10384653508663177,0.10268606245517731,0.15229427814483643,0.04837430641055107,0.16295574605464935,0.07669967412948608,0.10837741941213608,0.20780839025974274,0.1647995561361313,0.08732756972312927,0.17582660913467407,0.08247353881597519,0.09311560541391373,0.07691922783851624,0.06654483079910278,0.11644130200147629,0.0823550894856453,0.15861189365386963,0.3322147727012634,0.05606379732489586,0.3017750382423401,0.0976448804140091,0.0928005576133728,0.24935248494148254,0.13604427874088287,0.08668695390224457,0.11055583506822586,0.03259003162384033,0.09344004839658737,0.1185687705874443,0.22015826404094696,0.16263310611248016,0.17077475786209106,0.04304032772779465,0.11112154275178909,0.11312838643789291,0.11095695197582245,0.19084294140338898,0.13980069756507874,0.12267733365297318,0.07644899189472198,0.07518352568149567,0.15494205057621002,0.09442895650863647,0.11706937104463577,0.2254781723022461,0.15395468473434448,0.08008195459842682,0.09096622467041016,0.08261856436729431,0.04525664448738098,0.058317795395851135,0.08855399489402771,0.07033942639827728,0.15852701663970947,0.1271200329065323,0.08615870028734207,0.45595404505729675,0.12958182394504547,0.1591208130121231,0.12829838693141937,0.12283137440681458,0.2674117386341095,0.24401715397834778,0.07645771652460098,0.0644148662686348,0.3547353148460388,0.11061602830886841,0.22847910225391388,0.1534062772989273,0.08086016029119492,0.12044405937194824,0.12399018555879593,0.12086440622806549,0.10836800187826157,0.0626671090722084,0.09732617437839508,0.36969366669654846,0.5980567336082458,0.1581244170665741,0.1286848485469818,0.10484433174133301,0.18781554698944092,0.26943719387054443,0.14307016134262085,0.3310023546218872,0.10256963223218918,0.09996545314788818,0.1276131421327591,0.07802407443523407,0.05989062413573265,0.08565858751535416,0.14111681282520294,0.31374675035476685,0.057487525045871735,0.06271997094154358,0.2815459966659546,0.09550242125988007,0.292651504278183,0.1436575949192047,0.22764980792999268,0.07682006061077118,0.13406910002231598,0.23877379298210144,0.05491522327065468,0.09957578778266907,0.1009850800037384,0.1236744374036789,0.07674867659807205,0.11417458951473236,0.0788702443242073,0.08413108438253403,0.20681259036064148,0.2216179072856903,0.09247598797082901,0.19327618181705475,0.38659799098968506,0.10688488185405731,0.13487964868545532,0.5517457127571106,0.24274656176567078,0.10458191484212875,0.1510535329580307,0.21648451685905457,0.09976114332675934,0.12463276088237762,0.16220112144947052,0.1456686556339264,0.1530982255935669,0.165522038936615,0.1366771012544632,0.2941662073135376,0.10990080237388611,0.09539404511451721,0.05753724277019501,0.05930902808904648,0.052772536873817444,0.11673673987388611,0.10272014886140823,0.08235377818346024,0.1931796818971634,0.11315491795539856,0.1126512959599495,0.10598877817392349,0.09244681149721146,0.11111268401145935,0.11202441900968552,0.15416203439235687,0.12875552475452423,0.13502125442028046,0.10365864634513855,0.07324061542749405,0.3269517719745636,0.22440075874328613,0.20203983783721924,0.1261696219444275,0.09898978471755981,0.1140916720032692,0.12309243530035019,0.3907316327095032,0.09472087770700455,0.1266862004995346,0.11749576777219772,0.38984471559524536,0.17597074806690216,0.4995318353176117,0.12017760425806046,0.15314148366451263,0.12075813114643097,0.11928095668554306,0.03461191803216934,0.08895940333604813,0.08862574398517609,0.08389338105916977,0.06754214316606522,0.032753270119428635,0.04567136615514755,0.07708721607923508,0.10241064429283142,0.09860379993915558,0.05151406303048134,0.1417955607175827,0.08747548609972,0.07711651176214218,0.2487911581993103,0.1578090786933899,0.06248859316110611,0.07389698177576065,0.09415922313928604,0.06978622823953629,0.2027314007282257,0.13677966594696045,0.14802932739257812,0.2154434323310852,0.07762382179498672,0.13773927092552185,0.14559762179851532,0.12265770882368088,0.07031255960464478,0.07040614634752274,0.1462949514389038,0.18461322784423828,0.14017723500728607,0.13383451104164124,0.31139272451400757,0.15106357634067535,0.11513643711805344,0.08937772363424301,0.07694367319345474,0.13560089468955994,0.2531644105911255,0.1173866018652916,0.12672054767608643,0.11916015297174454,0.07617034763097763,0.12768341600894928,0.06849560141563416,0.09987017512321472,0.12322696298360825,0.10303563624620438,0.18561799824237823,0.10513371229171753,0.09507426619529724,0.10348813235759735,0.022275611758232117,0.07189042121171951,0.1563062220811844,0.16352242231369019,0.1274128407239914,0.05810664966702461,0.12138155847787857,0.08759325742721558,0.03331257402896881,0.12222889065742493,0.059914398938417435,0.0649440735578537,0.0853671133518219,0.10375186800956726,0.18194334208965302,0.20966613292694092,0.12744905054569244,0.22026388347148895,0.12575051188468933,0.16017623245716095,0.07554847747087479,0.07551529258489609,0.07342598587274551,0.27255168557167053,0.11601611971855164,0.05624420940876007,0.21253636479377747,0.055547334253787994,0.15005497634410858,0.3864334523677826,0.10235416144132614,0.11648363620042801,0.06549984961748123,0.34628477692604065,0.4043343663215637,0.25899308919906616,0.13878270983695984,0.09851451218128204,0.1746237874031067,0.27796435356140137,0.13271121680736542,0.13141204416751862,0.15959663689136505,0.08265605568885803,0.13297876715660095,0.39318424463272095,0.06619203835725784,0.11066139489412308,0.11194116622209549,0.09816081821918488,0.10698016732931137,0.14876937866210938,0.06273136287927628,0.1478928029537201,0.11058033257722855,0.17449209094047546,0.13835617899894714,0.18212752044200897,0.142900750041008,0.07846784591674805,0.20545578002929688,0.12954211235046387,0.1409827619791031,0.10390587896108627,0.31461745500564575,0.09579360485076904,0.05964547023177147,0.18152326345443726,0.014576052315533161,0.18631887435913086,0.07481418550014496,0.29056769609451294,0.22801215946674347,0.04449266567826271,0.0898783728480339,0.11226531863212585,0.22954460978507996,0.14397461712360382,0.2038676142692566,0.08946264535188675,0.08384735137224197,0.09006943553686142,0.7547230124473572,0.0984078198671341,0.0884462296962738,0.08374717086553574,0.1451071947813034,0.13957351446151733,0.1463240385055542,0.5057648420333862,0.058306481689214706,0.09109607338905334,0.06376396119594574,0.11002049595117569,0.09268682450056076,0.35457316040992737,0.13019892573356628,0.21817690134048462,0.10695645213127136,0.15790417790412903,0.143382728099823,0.09910248965024948,0.2729877233505249,0.12957748770713806,0.08398124575614929,0.09931724518537521,0.05781490355730057,0.051178015768527985,0.069147028028965,0.07408038526773453,0.13568660616874695,0.5626644492149353,0.1013617217540741,0.06675563007593155,0.1504683792591095,0.07521329820156097,0.08821794390678406,0.07180816680192947,0.23648563027381897,0.09067526459693909,0.17667408287525177,0.24926522374153137,0.11649642884731293,0.12663958966732025,0.1325969696044922,0.12278355658054352,0.13172344863414764,0.19871489703655243,0.3054369390010834,0.11921809613704681,0.05111074820160866,0.32195597887039185,0.33247652649879456,0.12397434562444687,0.04673609882593155,0.11230997741222382,0.07290992885828018,0.1318138688802719,0.24066641926765442,0.11945394426584244,0.1901199370622635,0.07735829800367355,0.06083860248327255,0.10099620372056961,0.21332992613315582,0.058766141533851624,0.11323206126689911,0.04382286220788956,0.13544683158397675,0.19742223620414734,0.14809207618236542,0.09032037854194641,0.26035940647125244,0.027943124994635582,0.13465382158756256,0.14805860817432404,0.06569306552410126,0.10928289592266083,0.20141226053237915,0.11140131205320358,0.09391319006681442,0.13745388388633728,0.22451800107955933,0.033330127596855164,0.22514784336090088,0.26122161746025085,0.12169192731380463,0.12236671149730682,0.08647149801254272,0.31405070424079895,0.10427544265985489,0.14715394377708435,0.27878937125205994,0.08256983757019043,0.05220172181725502,0.0661797896027565,0.12012091279029846,0.15561267733573914,0.11750641465187073,0.03309611231088638,0.1399158239364624,0.06723648309707642,0.05222132429480553,0.05940358713269234,0.16940093040466309,0.15788500010967255,0.08946491777896881,0.09498381614685059,0.12298174202442169,0.1879250705242157,0.15414178371429443,0.2227248251438141,0.17414067685604095,0.033976249396800995,0.19799001514911652,0.11035913228988647,0.10162795335054398,0.1354445219039917,0.15381990373134613,0.3451034724712372,0.16929548978805542,0.09410697966814041,0.1047583594918251,0.08288384228944778,0.1342555433511734,0.08806733787059784,0.1168307289481163,0.10769761353731155,0.1213676854968071,0.13020019233226776,0.10327515751123428,0.048740413039922714,0.0913933664560318,0.05238327011466026,0.08785887062549591,0.12904581427574158,0.09486117213964462,0.12966826558113098,0.13190947473049164,0.09536097198724747,0.13484399020671844,0.1289530098438263,0.07548590004444122,0.22570332884788513,0.1202680692076683,0.13078072667121887,0.29921039938926697,0.10468260943889618,0.1615966111421585,0.30692410469055176,0.27443093061447144,0.09832839667797089,0.0642845630645752,0.2041177749633789,0.11244378238916397,0.24051381647586823,0.2102266550064087,0.2114560604095459,0.27577826380729675,0.1699846088886261,0.08985736221075058,0.2847496271133423,0.1859859973192215,0.11761587113142014,0.08684047311544418,0.044022418558597565,0.09394551813602448,0.10955178737640381,0.10031849145889282,0.09061092138290405,0.08092109858989716,0.2101949155330658,0.23519288003444672,0.11124075949192047,0.21045011281967163,0.2210131734609604,0.08982647955417633,0.13641491532325745,0.05837806314229965,0.046200018376111984,0.07860253006219864,0.12569235265254974,0.09085196256637573,0.18150292336940765,0.10838136821985245,0.2038360983133316,0.08261360973119736,0.06957127898931503,0.08765488117933273,0.19265712797641754,0.23849882185459137,0.24468058347702026,0.2708589732646942,0.06332756578922272,0.15455152094364166,0.2534129023551941,0.11912170052528381,0.10997053980827332,0.07539459317922592,0.13850301504135132,0.08971735090017319,0.13732293248176575,0.2507941424846649,0.1334766149520874,0.12179353088140488,0.10266275703907013,0.09818175435066223,0.10206145793199539,0.1618514358997345,0.10763779282569885,0.29602766036987305,0.06944222748279572,0.058866679668426514,0.11098621785640717,0.1306443065404892,0.07739806175231934,0.40198054909706116,0.08714831620454788,0.10164174437522888,0.17478643357753754,0.07352733612060547,0.09753495454788208,0.09878166764974594,0.042399998754262924,0.19939085841178894,0.1090303286910057,0.07204721868038177,0.19415250420570374,0.28148990869522095,0.0824105367064476,0.12537865340709686,0.12088333815336227,0.08330531418323517,0.14146175980567932,0.36331528425216675,0.1433158665895462,0.13786476850509644,0.12445402145385742,0.09153693169355392,0.09403099119663239,0.12276630848646164,0.25548431277275085,0.12489527463912964,0.30836021900177,0.149044930934906,0.17749746143817902,0.15762215852737427,0.054435085505247116,0.08638810366392136,0.07398129254579544,0.4485032856464386,0.05073665827512741,0.14853115379810333,0.13706833124160767,0.11748477816581726,0.1194147989153862,0.12358666956424713,0.09273224323987961,0.2147010862827301,0.36141252517700195,0.20730401575565338,0.22116750478744507,0.1791827231645584,0.6199762225151062,0.12528805434703827,0.2960911691188812,0.06661174446344376,0.1636132001876831,0.0968615710735321,0.15754149854183197,0.20060847699642181,0.1655111461877823,0.05804586783051491,0.19918033480644226,0.1263086348772049,0.1664685755968094,0.11800133436918259,0.058606043457984924,0.5085265040397644,0.06629838049411774,0.4952542781829834,0.15743887424468994,0.11676288396120071,0.2615610361099243,0.11119445413351059,0.05864682048559189,0.12694156169891357,0.21169182658195496,0.0820072591304779,0.10330554097890854,0.12529945373535156,0.06447618454694748,0.15625421702861786,0.3270021677017212,0.14216695725917816,0.166553795337677,0.04784146323800087,0.11507324129343033,0.09681440144777298,0.036199573427438736,0.15983055531978607,0.08003764599561691,0.04886844754219055,0.10141568630933762,0.27147912979125977,0.017588786780834198,0.1499309092760086,0.13180972635746002,0.14961209893226624,0.09206653386354446,0.2711586356163025,0.09762600064277649,0.07434643805027008,0.16189584136009216,0.14204753935337067,0.14309321343898773,0.20211808383464813,0.28530049324035645,0.21609681844711304,0.10543974488973618,0.043227389454841614,0.296996533870697,0.269819051027298,0.11121249198913574,0.06654993444681168,0.12148938328027725,0.2644142508506775,0.7582221627235413,0.16930361092090607,0.31956321001052856,0.31310340762138367,0.05795266479253769,0.31009820103645325,0.11722148954868317,0.2898539900779724,0.04182546213269234,0.12790518999099731,0.16388870775699615,0.17977790534496307,0.1004004031419754,0.07865463942289352,0.10433541238307953,0.08276034146547318,0.23135076463222504,0.4058808982372284,0.11885420978069305,0.12498151510953903,0.08914279192686081,0.1344328224658966,0.14070704579353333,0.09174464643001556,0.08349285274744034,0.10403209924697876,0.09271888434886932,0.15199628472328186,0.19082915782928467,0.055727481842041016,0.1939915269613266,0.155239537358284,0.05300620570778847,0.1254749298095703,0.05076245963573456,0.09478574991226196,0.12100181728601456,0.06126420944929123,0.13064119219779968,0.07900027185678482,0.06303074955940247,0.0940171331167221,0.098543182015419,0.2754502296447754,0.09448818862438202,0.3096577227115631,0.1819436252117157,0.1329490691423416,0.1242159903049469,0.08812408149242401,0.09421928226947784,0.07606746256351471,0.09498845785856247,0.13231444358825684,0.206581249833107,0.06642699986696243,0.10290810465812683,0.11163745820522308,0.10568413883447647,0.07316651940345764,0.10977098345756531,0.14376921951770782,0.1187949851155281,0.03935429826378822,0.23480425775051117,0.17676092684268951,0.11430129408836365,0.2780628502368927,0.21696288883686066,0.10314539819955826,0.1619957834482193,0.2587656080722809,0.02892148494720459,0.10177858918905258,0.13061808049678802,0.1109074130654335,0.07907693088054657,0.09676996618509293,0.19472499191761017,0.07745768129825592,0.08368994295597076,0.5145180821418762,0.08680656552314758,0.06497001647949219,0.0876544937491417,0.04667167365550995,0.16566835343837738,0.08764562010765076,0.09299209713935852,0.18002794682979584,0.21647430956363678,0.147914856672287,0.09555404633283615,0.5835769772529602,0.12068136781454086,0.2885603904724121,0.1422184854745865,0.06695860624313354,0.10496853291988373,0.08144086599349976,0.0694267749786377,0.10641706734895706,0.17502246797084808,0.17317511141300201,0.16102458536624908,0.062353163957595825,0.2633056044578552,0.2173054963350296,0.18903116881847382,0.2219320386648178,0.07666133344173431,0.07728013396263123,0.11621811240911484,0.20378533005714417,0.25379887223243713,0.15228036046028137,0.18636178970336914,0.0919063612818718,0.08233628422021866,0.3070477545261383,0.24801009893417358,0.042716898024082184,0.7446342706680298,0.09899606555700302,0.13963353633880615,0.13145768642425537,0.05806877836585045,0.14188629388809204,0.10451823472976685,0.11440061777830124,0.16033728420734406,0.14229728281497955,0.11824817955493927,0.22213658690452576,0.06255237013101578,0.10998779535293579,0.0869273841381073,0.17740175127983093,0.06691305339336395,0.18281251192092896,0.11933346837759018,0.18305762112140656,0.24250245094299316,0.11511058360338211,0.16824789345264435,0.1391524225473404,0.09679137915372849,0.24182851612567902,0.0906950905919075,0.1663481444120407,0.16969387233257294,0.24175922572612762,0.10131821036338806,0.9445261359214783,0.2084599882364273,0.08986591547727585,0.16839498281478882,0.20324893295764923,0.16035349667072296,0.12253091484308243,0.09817305207252502,0.0484943762421608,0.11976782977581024,0.16378068923950195,0.3680213987827301,0.12008535861968994,0.11859438568353653,0.12368170917034149,0.11478248238563538,0.22279183566570282,0.03632049262523651,0.08105872571468353,0.07868987321853638,0.11915872991085052,0.40149059891700745,0.43474724888801575,0.307621568441391,0.13798536360263824,0.17479723691940308,0.10989680886268616,0.3346087634563446,0.1062646210193634,0.09583738446235657,0.06876236200332642,0.10543938726186752,0.1745179146528244,0.05752348527312279,0.17297956347465515,0.127218097448349,0.2332029938697815,0.44724002480506897,0.1505296230316162,0.13957887887954712,0.14462816715240479,0.08180120587348938,0.077362060546875,0.12205977737903595,0.09431823343038559,0.10081909596920013,0.07819844037294388,0.1466774344444275,0.26009121537208557,0.19165140390396118,0.0771738663315773,0.10666102916002274,0.08561591804027557,0.11897097527980804,0.11207841336727142,0.25810185074806213,0.15551859140396118,0.0560268834233284,0.09126266092061996,0.18531107902526855,0.0904519185423851,0.08550241589546204,0.10960755497217178,0.17990437150001526,0.07960930466651917,0.19403840601444244,0.052487220615148544,0.14133700728416443,0.26503053307533264,0.13673309981822968,0.20204703509807587,0.17477206885814667,0.09968718886375427,0.20045730471611023,0.17921586334705353,0.09848792850971222,0.09842722862958908,0.1356208175420761,0.06950630992650986,0.6426342725753784,0.23427937924861908,0.1234605684876442,0.10495229810476303,0.17500372231006622,0.5626912713050842,0.11802586913108826,0.06812624633312225,0.08948738873004913,0.08281634002923965,0.09244517982006073,0.2904590368270874,0.27828091382980347,0.16479822993278503,0.09732457995414734,0.5535127520561218,0.20724354684352875,0.21067355573177338,0.15139879286289215,0.10658303648233414,0.10934332013130188,0.14493946731090546,0.39918527007102966,0.22722864151000977,0.3513590693473816,0.27224278450012207,0.07227955758571625,0.12178654223680496,0.1659807562828064,0.13930989801883698,0.2301662117242813,0.07737547159194946,0.08950480073690414,0.19662481546401978,0.1377222239971161,0.08267104625701904,0.13188369572162628,0.1508006602525711,0.16020922362804413,0.22947858273983002,0.07178983092308044,0.4048261344432831,0.15489284694194794,0.12648095190525055,0.10935475677251816,0.09459001570940018,0.07025440037250519,0.09121040999889374,0.24330931901931763,0.20051714777946472,0.04327693209052086,0.373149037361145,0.38132211565971375,0.08780896663665771,0.11081884801387787,0.16454049944877625,0.11367528140544891,0.16477540135383606,0.1038021594285965,0.14093424379825592,0.2687855362892151,0.1870814710855484,0.2727583646774292,0.11941052228212357,0.06554734706878662,0.12301105260848999,0.11307098716497421,0.09707487374544144,0.20011404156684875,0.3082689046859741,0.11236023157835007,0.14497600495815277,0.16103409230709076,0.09263366460800171,0.16396121680736542,0.11910782754421234,0.0699164867401123,0.15397723019123077,0.12445681542158127,0.1254497915506363,0.16963796317577362,0.09356637299060822,0.20107294619083405,0.18226976692676544,0.1406116932630539,0.07585393637418747,0.05581023916602135,0.2669678330421448,0.115972138941288,0.10538963228464127,0.1471502035856247,0.07375717163085938,0.23056533932685852,0.20717787742614746,0.16303551197052002,0.11760103702545166,0.05714115872979164,0.08129531145095825,0.14352232217788696,0.1945541650056839,0.06899235397577286,0.20762328803539276,0.10079193115234375,0.08968476206064224,0.17187176644802094,0.1546921730041504,0.035002272576093674,0.1232820376753807,0.2013619989156723,0.17615479230880737,0.168966606259346,0.11553418636322021,0.07569414377212524,0.15028807520866394,0.15420225262641907,0.05294593796133995,0.10151137411594391,0.135402649641037,0.033138059079647064,0.06289192289113998,0.14104072749614716,0.08790408074855804,0.16081126034259796,0.09660425037145615,0.11795896291732788,0.06264229118824005,0.08522790670394897,0.39534351229667664,0.2687934637069702,0.09051887691020966,0.06798438727855682,0.050743069499731064,0.08036798238754272,0.11231323331594467,0.03326505422592163,0.1292172372341156,0.09902503341436386,0.0885920524597168,0.20533150434494019,0.13849514722824097,0.07029435783624649,0.07071331143379211,0.0832705944776535,0.16602933406829834,0.09633489698171616,0.12415451556444168,0.190480574965477,0.12222852557897568,0.19262298941612244,0.0636577159166336,0.34483516216278076,0.2695302367210388,0.08931470662355423,0.26895150542259216,0.2630249261856079,0.07564374059438705,0.11553791910409927,0.4727908670902252,0.02658507600426674,0.20032580196857452,0.09514126181602478,0.11517741531133652,0.16925503313541412,0.35774073004722595,0.26139965653419495,0.0913894772529602,0.10808110982179642,0.0977686420083046,0.053658727556467056,0.10510782152414322,0.04020930826663971,0.18056680262088776,0.2519565522670746,0.09390394389629364,0.15614335238933563,0.09120243042707443,0.04984232410788536,0.14274173974990845,0.18800568580627441,0.10468874871730804,0.06339795887470245,0.062170568853616714,0.10295828431844711,0.0978168174624443,0.07772290706634521,0.15184693038463593,0.05058196187019348,0.16423286497592926,0.1336202174425125,0.04545922204852104,0.059900492429733276,0.12128540128469467,0.09706167131662369,0.11736094206571579,0.19790448248386383,0.09350766241550446,0.14945083856582642,0.07976339012384415,0.3315016031265259,0.20649310946464539,0.08223216980695724,0.2544735074043274,0.1324108988046646,0.29828280210494995,0.14615948498249054,0.3661753535270691,0.06005046144127846,0.08101842552423477,0.06001543253660202,0.05472347140312195,0.09667413681745529,0.2497311234474182,0.1030912920832634,0.0617406964302063,0.15902592241764069,0.10470002889633179,0.11098942160606384,0.2211887389421463,0.06696430593729019,0.09560272842645645,0.09445492178201675,0.2388685941696167,0.18695126473903656,0.16207237541675568,0.23796415328979492,0.10908155143260956,0.3559718132019043,0.06133772060275078,0.08870691806077957,0.15194077789783478,0.13638794422149658,0.11913581937551498,0.2407417893409729,0.20090216398239136,0.09062139689922333,0.09886934608221054,0.11007914692163467,0.36329707503318787,0.0796918123960495,0.08605795353651047,0.1318288892507553,0.17862680554389954,0.2119966447353363,0.16816827654838562,0.07148187607526779,0.14345140755176544,0.27426770329475403,0.15229278802871704,0.23111823201179504,0.20147743821144104,0.11736121773719788,0.14371618628501892,0.18066143989562988,0.03884834051132202,0.05404144898056984,0.0999360978603363,0.20393848419189453,0.15784621238708496,0.09330719709396362,0.1658918410539627,0.1698198765516281,0.11691359430551529,0.1657104343175888,0.17547018826007843,0.06618709117174149,0.35492652654647827,0.07036269456148148,0.16767485439777374,0.189410001039505,0.08190999925136566,0.13882794976234436,0.08263880014419556,0.09447329491376877,0.2335273027420044,0.07997861504554749,0.10692146420478821,0.12168678641319275,0.04740447551012039,0.21911470592021942,0.11061806976795197,0.18544752895832062,0.1287885308265686,0.14351731538772583,0.06164681166410446,0.06537194550037384,0.09374221414327621,0.1041671484708786,0.08117709308862686,0.24935391545295715,0.1434870809316635,0.0805925652384758,0.21300281584262848,0.14174343645572662,0.1200854703783989,0.026053722947835922,0.29226770997047424,0.37642404437065125,0.09735295921564102,0.08144130557775497,0.31701505184173584,0.040114592760801315,0.15978838503360748,0.09192387759685516,0.1242407038807869,0.07873211055994034,0.16892513632774353,0.08679699897766113,0.1213357150554657,0.1759157031774521,0.1466238796710968,0.07756446301937103,0.0933886393904686,0.12346017360687256,0.10382355749607086,0.16842088103294373,0.07839873433113098,0.11171931028366089,0.10721150040626526,0.2598833441734314,0.33827751874923706,0.11050491780042648,0.08886066824197769,0.22836777567863464,0.27590736746788025,0.17692044377326965,0.12992066144943237,0.47828835248947144,0.6885162591934204,0.14742209017276764,0.28670626878738403,0.15246133506298065,0.15782256424427032,0.06666208803653717,0.11304349452257156,0.07318107038736343,0.0658600926399231,0.25254225730895996,0.11701765656471252,0.03859008476138115,0.2543429434299469,0.22504746913909912,0.13419859111309052,0.15123528242111206,0.18574529886245728,0.07650300115346909,0.16992072761058807,0.07188408076763153,0.13543739914894104,0.1476544290781021,0.15800480544567108,0.17176669836044312,0.13873018324375153,0.09609419852495193,0.07153988629579544,0.13436652719974518,0.3268677890300751,0.15872888267040253,0.13995443284511566,0.2297516018152237,0.0968099907040596,0.14065119624137878,0.027757126837968826,0.2335529774427414,0.06675805151462555,0.05043496936559677,0.20878632366657257,0.21051041781902313,0.10933229327201843,0.1697397083044052,0.09635171294212341,0.19831734895706177,0.05428900942206383,0.21498548984527588,0.08870135992765427,0.06166353076696396,0.09473784267902374,0.08780144155025482,0.07707656174898148,0.1544603854417801,0.11841534823179245,0.07130230218172073,0.06627476960420609,0.28679099678993225,0.19704841077327728,0.07032717019319534,0.14382700622081757,0.08692017942667007,0.23165878653526306,0.25883060693740845,0.10426151752471924,0.06557228416204453,0.09824316203594208,0.24203981459140778,0.0387183241546154,0.07654502987861633,0.08052356541156769,0.12778793275356293,0.20347343385219574,0.07065407931804657,0.17101408541202545,0.1672145128250122,0.18660888075828552,0.18795283138751984,0.11986298114061356,0.10073628276586533,0.06582190841436386,0.05964192748069763,0.20022372901439667,0.09883596748113632,0.17047584056854248,0.09065904468297958,0.09436547756195068,0.24705937504768372,0.4483334720134735,0.10085699707269669,0.19259053468704224,0.11355006694793701,0.11540581285953522,0.14295515418052673,0.053729329258203506,0.279267281293869,0.14239364862442017,0.06959538161754608,0.17553430795669556,0.0751994252204895,0.14330971240997314,0.14986559748649597,0.19267569482326508,0.33706384897232056,0.2133343368768692,0.08421657979488373,0.10253016650676727,0.2777722477912903,0.13458006083965302,0.11772052198648453,0.07023316621780396,0.13194602727890015,0.30564042925834656,0.11517973989248276,0.14081741869449615,0.04147728905081749,0.12221509963274002,0.08106079697608948,0.11794248223304749,0.09677457064390182,0.09722500294446945,0.07820314913988113,0.15797577798366547,0.06417196989059448,0.05974205955862999,0.24439428746700287,0.1118461862206459,0.20158694684505463,0.11116470396518707,0.16620449721813202,0.18238620460033417,0.0911068394780159,0.10759615153074265,0.07553137093782425,0.1095968633890152,0.4072153568267822,0.09903407096862793,0.17573052644729614,0.0897192507982254,0.09831425547599792,0.32531145215034485,0.13391850888729095,0.13385561108589172,0.15717560052871704,0.20662662386894226,0.0539260134100914,0.09034983813762665,0.11792965233325958,0.10137152671813965,0.04329914599657059,0.1491130292415619,0.196264386177063,0.15868720412254333,0.14182037115097046,0.05685708299279213,0.1205383911728859,0.06388460844755173,0.08223126083612442,0.16933098435401917,0.06606096029281616,0.17145486176013947,0.07586494833230972,0.12649540603160858,0.24482449889183044,0.1349399983882904,0.20955976843833923,0.1397973895072937,0.08914708346128464,0.1968986988067627,0.10671934485435486,0.22979727387428284,0.14843769371509552,0.22457589209079742,0.12327470630407333,0.2514396905899048,0.09238694608211517,0.13319870829582214,0.2023511379957199,0.05417492985725403,0.292450487613678,0.09645862132310867,0.29434868693351746,0.18899936974048615,0.358776330947876,0.08386124670505524,0.10061543434858322,0.08131100237369537,0.0860772430896759,0.10353147983551025,0.06446079909801483,0.07484723627567291,0.1256832331418991,0.08837594091892242,0.13606497645378113,0.07267795503139496,0.04154588282108307,0.11312337219715118,0.1246408000588417,0.08250599354505539,0.19554516673088074,0.07278116047382355,0.12697318196296692,0.3415738642215729,0.12770803272724152,0.18132223188877106,0.24853262305259705,0.0640723928809166,0.22573800384998322,0.08123306185007095,0.13549993932247162,0.06600957363843918,0.061542924493551254,0.3999199867248535,0.10579407215118408,0.09761901944875717,0.13712123036384583,0.21943093836307526,0.16744457185268402,0.10140232741832733,0.07885289937257767,0.05851132422685623,0.15513281524181366,0.06828686594963074,0.16294531524181366,0.08346934616565704,0.17452827095985413,0.3055601418018341,0.10162345319986343,0.29158449172973633,0.09114160388708115,0.23363511264324188,0.1515592485666275,0.056825026869773865,0.19727855920791626,0.03514397144317627,0.08053398132324219,0.12284376472234726,0.06535395234823227,0.08543313294649124,0.11131112277507782,0.07930300384759903,0.1615389585494995,0.32136163115501404,0.4793369472026825,0.06426124274730682,0.10079634934663773,0.1052413284778595,0.1188017874956131,0.16127528250217438,0.09721445292234421,0.10813556611537933,0.1612289547920227,0.18159399926662445,0.16697680950164795,0.18817399442195892,0.102135568857193,0.1948394626379013,0.17300604283809662,0.10838192701339722,0.09743327647447586,0.1947493851184845,0.12711678445339203,0.12290557473897934,0.16507181525230408,0.1212206557393074,0.08668367564678192,0.15726494789123535,0.18803687393665314,0.07429784536361694,0.05638666823506355,0.4822767376899719,0.10565099120140076,0.09716440737247467,0.10504039376974106,0.1678852140903473,0.12076009809970856,0.1499658077955246,0.2519035041332245,0.1425952911376953,0.17686228454113007,0.12119988352060318,0.05308707430958748,0.13578274846076965,0.1466304212808609,0.18033823370933533,0.23296330869197845,0.07643962651491165,0.21788690984249115,0.05566342547535896,0.04439409077167511,0.1565948873758316,0.14094778895378113,0.19665497541427612,0.060483600944280624,0.052270058542490005,0.26360511779785156,0.08665170520544052,0.09662611037492752,0.33153820037841797,0.11955805867910385,0.11571051180362701,0.13411502540111542,0.16927002370357513,0.0495813712477684,0.08559439331293106,0.15314900875091553,0.21033087372779846,0.05606355518102646,0.25986939668655396,0.07577893137931824,0.09521899372339249,0.04638706520199776,0.21522463858127594,0.07978145778179169,0.04525699466466904,0.17079703509807587,0.17101654410362244,0.38303041458129883,0.13482224941253662,0.13992051780223846,0.09143685549497604,0.0908443033695221,0.07781217992305756,0.07784625887870789,0.8836413025856018,0.13192889094352722,0.0652245581150055,0.09000127762556076,0.1539667397737503,0.07891898602247238,0.0649053081870079,0.17997649312019348,0.12729936838150024,0.21423524618148804,0.056511376053094864,0.05631338059902191,0.12725186347961426,0.16240328550338745,0.09438054263591766,0.13090242445468903,0.14820176362991333,0.08773645013570786,0.11011821776628494,0.22602500021457672,0.11494937539100647,0.07813989371061325,0.09848646819591522,0.3918883800506592,0.5773850679397583,0.13948416709899902,0.12488507479429245,0.056940432637929916,0.09458139538764954,0.12716792523860931,0.1343308538198471,0.12771490216255188,0.08097845315933228,0.020758286118507385,0.06323893368244171,0.14634637534618378,0.06742493063211441,0.19359827041625977,0.12148374319076538,0.13176728785037994,0.0816359743475914,0.21932171285152435,0.09608820080757141,0.1775815188884735,0.10292456299066544,0.273398220539093,0.08870670199394226,0.06033298000693321,0.11653263121843338,0.19447572529315948,0.16836103796958923,0.0766492635011673,0.15691430866718292,0.17580178380012512,0.0787992924451828,0.08763734251260757,0.20402054488658905,0.05332502722740173,0.05802685022354126,0.08551616966724396,0.07563314586877823,0.2029086947441101,0.2929418385028839,0.46810364723205566,0.11665891110897064,0.09731297940015793,0.09713707864284515,0.13498467206954956,0.10112413018941879,0.1037730947136879,0.25759997963905334,0.059197645634412766,0.10920750349760056,0.14936749637126923,0.254415363073349,0.2368611991405487,0.22126036882400513,0.06656190007925034,0.107764333486557,0.1365082859992981,0.22430342435836792,0.056694019585847855,0.11681656539440155,0.01693880185484886,0.1375933736562729,0.2596800923347473,0.12025833129882812,0.18291528522968292,0.18070413172245026,0.06146973371505737,0.3223823010921478,0.11440089344978333,0.16503731906414032,0.14285807311534882,0.10446183383464813,0.11969900131225586,0.053693078458309174,0.08195432275533676,0.06207779049873352,0.16024774312973022,0.05062359571456909,0.14165879786014557,0.08147381991147995,0.12138130515813828,0.2705152928829193,0.10842502862215042,0.25681015849113464,0.17860466241836548,0.09592200815677643,0.10547652840614319,0.12396075576543808,0.14314153790473938,0.2619384229183197,0.07964058220386505,0.12827983498573303,0.12565955519676208,0.2593211531639099,0.0675564855337143,0.267130047082901,0.1108289584517479,0.08948256820440292,0.1814543604850769,0.09265745431184769,0.10816630721092224,0.2375076413154602,0.08768909424543381,0.052522800862789154,0.048487454652786255,0.19057124853134155,0.47059136629104614,0.12596039474010468,0.17442695796489716,0.09965123981237411,0.27044519782066345,0.20260411500930786,0.08626914769411087,0.30836251378059387,0.14278236031532288,0.009176026098430157,0.05882832407951355,0.046245694160461426,0.12478084862232208,0.07761156558990479,0.12610994279384613,0.2871316075325012,0.0796290710568428,0.07835450023412704,0.28417402505874634,0.34385979175567627,0.12259697169065475,0.09994319826364517,0.0649624839425087,0.22862939536571503,0.22946786880493164,0.04125167801976204,0.0662035271525383,0.08685620129108429,0.10164443403482437,0.11774014681577682,0.08261995762586594,0.17210450768470764,0.08507820218801498,0.12221376597881317,0.2523247003555298,0.17143769562244415,0.2026703655719757,0.07237505912780762,0.12687146663665771,0.06387178599834442,0.19679737091064453,0.07834813743829727,0.2857864201068878,0.17765891551971436,0.0839756578207016,0.25382331013679504,0.07201707363128662,0.13515055179595947,0.15986602008342743,0.31604114174842834,0.04973175749182701,0.18820947408676147,0.25214189291000366,0.061685387045145035,0.07974030822515488,0.16686396300792694,0.03295116871595383,0.2135031670331955,0.11700565367937088,0.11378145962953568,0.07461181282997131,0.07203295826911926,0.07935534417629242,0.10741478949785233,0.0925682857632637,0.25932809710502625,0.5381016731262207,0.091890849173069,0.10739792138338089,0.05576501041650772,0.11650843918323517,0.13095994293689728,0.07964006811380386,0.11194055527448654,0.13771626353263855,0.07255314290523529,0.15348802506923676,0.31767019629478455,0.1087757870554924,0.0581974983215332,0.345012903213501,0.11180227994918823,0.28063079714775085,0.15063822269439697,0.16940702497959137,0.14551594853401184,0.08344212174415588,0.15099366009235382,0.08387064933776855,0.1902029812335968,0.10953851789236069,0.10721040517091751,0.09662243723869324,0.14112798869609833,0.13058771193027496,0.06102820113301277]],"used_time":43,"message":"ok"}}
         */
        // 行人重识别	/neuhub/personreid
        @Test
        public void personreid() {
            byte[] data = dataBinary(picture);
            HttpEntity<Object> requestEntity = new HttpEntity<>(data);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/personreid";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }



        /**
         * 调用结果: {"code":"10000","charge":true,"remain":998,"remainTimes":998,"remainSeconds":-1,"msg":"查询成功,扣费","result":[{"image":"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","used_time":121,"message":"ok","status":200}]}
         */
        // 人脸解析	/neuhub/face_parser
        @Test
        public void face_parser() {
            byte[] data = dataBinary(picture);

            ArrayList<Map> list = new ArrayList<>();
            HashMap<Object, Object> paraMap = new HashMap<>();
            paraMap.put("image", imageBase64(data));
            list.add(paraMap);

            HttpEntity<Object> requestEntity = new HttpEntity<>(list);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/face_parser";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":498,"remainTimes":498,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"requestId":"d6c72000-a037-4057-b596-80991bc96823","status":0,"message":"success","label":0,"data":{"porn":{"pornScore":0.000001036993126035668,"vulgarScore":1.5260543761996814E-7,"sexyScore":0.000002505834117982886,"label":0},"terror":{"label":0,"flags":[],"riot":0,"bloodiness":0,"flame":0,"terrorists":[]},"text":{"label":0,"porn":{"label":0,"score":0,"words":[]},"politics":{"label":0,"score":0,"words":[]},"terror":{"label":0,"score":0,"words":[]}}}}}
         */
        // 通用图片审核	/neuhub/censor_image
        @Test
        public void censor_image() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            Map<String, Object> map = new HashMap<>();
            map.put("url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603453397017&di=55b67e589cfab6617f63047b04be4702&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F70%2F91%2F01300000261284122542917592865.jpg");

            // scenes ['porn', 'terror', 'text'...]
            String[] arr = {"porn", "terror", "text"};
            map.put("scenes", arr);
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/censor_image";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":499,"remainTimes":499,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"requestId":"75487b7b-6f15-4576-ad82-ff86fb0a6a73","status":0,"message":"success","label":0,"data":{"porn":{"label":0,"score":0,"words":[]},"politics":{"label":0,"score":0,"words":[]},"terror":{"label":0,"score":0,"words":[]}}}}
         */
        // 文本智能审核	/neuhub/censor_text
        @Test
        public void censor_text() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            byte[] data = dataBinary(picture);
            String imageBase64 = imageBase64(data);
            Map<String, Object> map = new HashMap<>();
            map.put("text", "文本内容");
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/censor_text";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":498,"remainTimes":498,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"status":0,"requestId":"cab89bee-c911-4102-8493-e5faeeaac72d","message":"success","label":0,"data":{"qrcode":[],"logo":[],"sensitiveWord":[]}}}
         */
        // 广告检测	/neuhub/censor_ad
        @Test
        public void censor_ad() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            Map<String, Object> map = new HashMap<>();
            map.put("url", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3135215632,750931275&fm=26&gp=0.jpg");
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/censor_ad";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {
         *     "code": "10000",
         *     "charge": true,
         *     "remain": 497,
         *     "remainTimes": 497,
         *     "remainSeconds": -1,
         *     "msg": "查询成功,扣费",
         *     "result": {
         *         "status_code": 0,
         *         "message": "SUCCESS",
         *         "task_id": "3548/food/1603677495.254712",
         *         "failed_list": [{
         *                 "image_name": "Milk_salt_soda_v1",
         *                 "message": "REPEAT_IMAGE_NAME_IN_DB"
         *             }
         *         ]
         *     }
         * }
         */
        // 通用图片搜索_图片入库请求	/neuhub/index
        @Test
        public void index() {
            byte[] data = dataBinary(picture);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            // MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            HashMap<Object, Object> map = new HashMap<>();
            map.put("collection_name", "food");
            ArrayList<Object> list = new ArrayList<>();
            HashMap<Object, Object> paraMap = new HashMap<>();
            paraMap.put("image_name", "Milk_salt_soda_v1");
            paraMap.put("image_content", imageBase64(data));
            list.add(paraMap);
            map.put("docs", list);
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/index";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }

            result(responseEntity);
        }

        /**
         * "code": "10000", "charge": true, "remain": 490, "remainTimes": 490, "remainSeconds": -1, "msg": "查询成功,扣费", "result": {
         *     "status_code": 0,
         *     "message": "SUCCESS",
         *     "task_status": "FINISHED",
         *     "succeeded_list": [],
         *     "pending_list": [],
         *     "failed_list": []
         * }
         * }
         */
        // 通用图片搜索_任务状态查询请求	/neuhub/task
        @Test
        public void task() {
            byte[] data = dataBinary(picture);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
            // 设置请求参数
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

            map.add("task_id", "3548/food/1603450523.7668211");
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/task";
            ResponseEntity<String> responseEntity = null;
            try {
                String s = restTemplate.postForObject(requestUrl, requestEntity, String.class);
                responseEntity = new ResponseEntity(s, HttpStatus.valueOf(200));
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }

            result(responseEntity);
        }

        /**
         * 通用图片搜索_图片搜索请求	/neuhub/index_search
         * 调用结果: {
         *     "code": "10000",
         *     "charge": true,
         *     "remain": 499,
         *     "remainTimes": 499,
         *     "remainSeconds": -1,
         *     "msg": "查询成功,扣费",
         *     "result": {
         *         "status_code": 0,
         *         "message": "SUCCESS",
         *         "result": [{
         *                 "image_name": "Milk_salt_soda_v1",
         *                 "info": "",
         *                 "similarity": 0.9773102961590038
         *             }, {
         *                 "image_name": "双黄白莲蓉2",
         *                 "info": "something",
         *                 "similarity": 0.14431312056350648
         *             }, {
         *                 "image_name": "双黄白莲蓉1",
         *                 "info": "something",
         *                 "similarity": 0.12347231028804331
         *             }
         *         ]
         *     }
         * }
         */
        @Test
        public void index_search() {
            byte[] data = dataBinary(picture);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            HashMap<Object, Object> map = new HashMap<>();
            map.put("collection_name", "food");
            map.put("query_content", imageBase64(data));
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/index_search";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {
         *     "code": "10000",
         *     "charge": true,
         *     "remain": 485,
         *     "remainTimes": 485,
         *     "remainSeconds": -1,
         *     "msg": "查询成功,扣费",
         *     "result": {
         *         "status_code": 0,
         *         "message": "SUCCESS",
         *         "collection_list": [{
         *                 "collection_name": "food",
         *                 "indexed_image_count": 3
         *             }, {
         *                 "collection_name": "蔬菜",
         *                 "indexed_image_count": 1
         *             }
         *         ]
         *     }
         * }
         */
        // 通用图片搜索_库列表查询请求	/neuhub/collection_list
        @Test
        public void collection_list() {
            byte[] data = dataBinary(picture);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            HashMap<Object, Object> map = new HashMap<>();
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/collection_list";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }

            result(responseEntity);
        }

        /**
         * 通用图片搜索_图片删除请求	/neuhub/image_delete
         * 调用结果: {
         *     "code": "10000",
         *     "charge": true,
         *     "remain": 484,
         *     "remainTimes": 484,
         *     "remainSeconds": -1,
         *     "msg": "查询成功,扣费",
         *     "result": {
         *         "status_code": 0,
         *         "message": "SUCCESS",
         *         "succeeded_list": ["双黄白莲蓉2"],
         *         "failed_list": []
         *     }
         * }
         */
        @Test
        public void image_delete() {
            byte[] data = dataBinary(picture);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            HashMap<Object, Object> map = new HashMap<>();
            map.put("collection_name", "food");
            ArrayList<Object> list = new ArrayList<>();
            HashMap<Object, Object> paraMap = new HashMap<>();
            paraMap.put("image_name", "Milk_salt_soda_v1");
            list.add("双黄白莲蓉2");
            map.put("image_names", list);
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/image_delete";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }

            result(responseEntity);
        }

        /**
         * 调用结果: {"code":"10000","charge":true,"remain":484,"remainTimes":484,"remainSeconds":-1,"msg":"查询成功,扣费","result":{"status_code":0,"message":"SUCCESS","succeeded_list":[],"failed_list":[{"image_name":"Milk_salt_soda_v1","message":"INVALID_IMAGE_INFO"}]}}
         */
        // 通用图片搜索_图片信息更新请求	/neuhub/image_update
        @Test
        public void image_update() {
            byte[] data = dataBinary(picture);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            // MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            HashMap<Object, Object> map = new HashMap<>();
            map.put("collection_name", "food");
            ArrayList<Object> list = new ArrayList<>();
            HashMap<Object, Object> paraMap = new HashMap<>();
            paraMap.put("image_name", "双黄白莲蓉2");
            paraMap.put("info","other");
            paraMap.put("image_content", imageBase64(data));
            list.add(paraMap);
            map.put("docs", list);
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/image_update";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }

            result(responseEntity);
        }

        // 通用图片搜索_图片信息查询请求	/neuhub/image_fetch
        @Test
        public void image_fetch() {
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();  //表单数据结构
            form.add("collection_name", "food");
            ArrayList<Object> list = new ArrayList<>();
            list.add("双黄白莲蓉1");
            list.add("双黄白莲蓉2");
            form.add("image_names", list);

            HttpHeaders httpHeaders = new HttpHeaders();
            // httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
            httpHeaders.set("Content-Type", "application/json");
            HashMap<Object, Object> map = new HashMap<>();
            String s1 = JSONArray.toJSONString(list);
            map.put("collection_name", "food");
            map.put("image_names", list);
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/image_fetch";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }

            result(responseEntity);
        }

        /*车牌识别	/neuhub/license_plate
        调用结果: {
        "code": "10000",
        "charge": true,
        "remain": 495,
        "remainTimes": 495,
        "remainSeconds": -1,
        "msg": "查询成功,扣费",
        "result": {
            "message": "success",
            "request_id": "d41d8cd98f00b204e9800998ecf8427e",
            "resultData": [{
                    "text": "京N8P8F8",
                    "location": [8, 132, 729, 135, 728, 368, 7, 365],
                    "probability": 0.9979123473167419
                }
            ],
            "code": 0
        }
    }*/
        @Test
        public void license_plate() {
            byte[] data = dataBinary(picture);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            Map<String, Object> map = new HashMap<>();
            map.put("imageBase64Str", imageBase64(data));
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/license_plate";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 垃圾分类	/neuhub/garbageImageSearch
         * 调用结果: {
         * "code": "10000",
         * "charge": true,
         * "remain": 499,
         * "remainTimes": 499,
         * "remainSeconds": -1,
         * "msg": "查询成功,扣费",
         * "result": {
         * "status": 0,
         * "message": "success",
         * "garbage_info": [{
         * "cate_name": "可回收物",
         * "city_id": "310000",
         * "city_name": "上海市",
         * "confidence": 0.6788544188010546,
         * "garbage_name": "车牌架/牌照托",
         * "ps": "投放建议：投放可回收物时，应尽量保持清洁干燥，避免污染；立体包装应清空内容物，清洁后压扁投放；易破损或有尖锐边角的应包后投放。"
         * }
         * ]
         * }
         * }
         */
        @Test
        public void garbageImageSearch() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
            byte[] data = dataBinary(picture);
            String imageBase64 = imageBase64(data);
            Map<String, Object> map = new HashMap<>();
            map.put("imgBase64", imageBase64);
            map.put("cityId", "310000");
            HttpEntity<Object> requestEntity = new HttpEntity<>(map, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/garbageImageSearch";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 车辆检测	/neuhub/vehicle_detection
         * 调用结果: {
         * "code": "10000",
         * "charge": true,
         * "remain": 498,
         * "remainTimes": 498,
         * "remainSeconds": -1,
         * "msg": "查询成功,扣费",
         * "result": {
         * "status": 0,
         * "message": "ok",
         * "request_id": "1603444356.3422139",
         * "used_time": 29,
         * "vehicleDetectionResult": [[32, 120, 469, 320, 0.8687605857849121]]
         * }
         * }
         */
        @Test
        public void vehicle_detection() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/octet-stream");
            byte[] data = dataBinary(picture);
            HttpEntity<Object> requestEntity = new HttpEntity<>(data, httpHeaders);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/vehicle_detection";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 调用结果: {
         * "code": "10000",
         * "charge": true,
         * "remain": 997,
         * "remainTimes": 997,
         * "remainSeconds": -1,
         * "msg": "查询成功,扣费",
         * "result": [{
         * "result": "未知种类",
         * "used_time": 32,
         * "message": "ok",
         * "status": 200
         * }
         * ]
         * }
         */
        // 车型识别	/neuhub/car_class
        @Test
        public void car_class() {
            byte[] data = dataBinary(picture);

            ArrayList<Object> list = new ArrayList<>();
            HashMap<Object, Object> paraMap = new HashMap<>();
            paraMap.put("image", imageBase64(data));
            list.add(paraMap);
            HttpEntity<Object> requestEntity = new HttpEntity<>(list);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/car_class";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 犬类识别	/neuhub/dog_class
         * 调用结果: {
         * "code": "10000",
         * "charge": true,
         * "remain": 499,
         * "remainTimes": 499,
         * "remainSeconds": -1,
         * "msg": "查询成功,扣费",
         * "result": [{
         * "result": "未知种类(UNDECIDED)",
         * "used_time": 48,
         * "message": "ok",
         * "status": 200
         * }
         * ]
         * }
         */
        @Test
        public void dog_class() {
            byte[] data = dataBinary(picture);
            ArrayList<Object> list = new ArrayList<>();
            HashMap<Object, Object> paraMap = new HashMap<>();
            paraMap.put("image", imageBase64(data));
            list.add(paraMap);
            HttpEntity<Object> requestEntity = new HttpEntity<>(list);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/dog_class";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /**
         * 车辆解析	/neuhub/vehicle_parser
         * 调用结果:{
         * "code": "10000",
         * "charge": true,
         * "remain": 998,
         * "remainTimes": 998,
         * "remainSeconds": -1,
         * "msg": "查询成功,扣费",
         * "result": [{
         * "image": "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",
         * "used_time": 55,
         * "message": "ok",
         * "status": 200
         * }
         * ]
         * }
         */
        @Test
        public void vehicle_parser() {
            byte[] data = dataBinary(picture);
            ArrayList<Object> list = new ArrayList<>();
            HashMap<Object, Object> paraMap = new HashMap<>();
            paraMap.put("image", imageBase64(data));
            list.add(paraMap);
            HttpEntity<Object> requestEntity = new HttpEntity<>(list);

            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/vehicle_parser";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }

        /*
        * 返回结果
        *  < 200, {
        "code": "10000",
        "charge": true,
        "remain": 498,
        "remainTimes": 498,
        "remainSeconds": -1,
        "msg": "查询成功,扣费",
        "result": {
            "code": "1000",
            "msg": "Request OK!",
            "result": [{
                    "id": "7300075",
                    "probability": 0.16499730944633484
                }, {
                    "id": "7300062",
                    "probability": 0.07364558428525925
                }, {
                    "id": "7300024",
                    "probability": 0.05823536962270737
                }
            ]
        }
    }, {
        Date = [Fri, 23 Oct 2020 06: 45: 26 GMT],
        Content - Type = [application / json;
            charset = utf - 8],
        Content - Length = [317],
        Connection = [keep - alive],
        Server = [nginx / 1.16.1],
        Vary = [Accept - Encoding]
    }
    >
        *
        *
        * */
        // 智能分诊	/neuhub/intelligent_triage
        @Test
        public void intelligent_triage() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/json");
            //body请求参数
            Map<String, Object> postParameters = new HashMap<>();
            postParameters.put("description", "今天肚子不舒服");
            // postParameters.put("patientInformation", "{\"name\":\"张三\",\"age\":\"18\",\"sex\":\"男\"}");
            HttpEntity<Object> requestEntity = new HttpEntity<>(postParameters, httpHeaders);
            //以下参数仅为示例值
            String requestUrl = gatewayUrl + "/neuhub/intelligent_triage";
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class);
            } catch (Exception e) {
                //调用API失败，错误处理
                throw new RuntimeException(e);
            }
            result(responseEntity);
        }



    private String imageBase64(byte[] data) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    private byte[] dataBinary(String addr) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(addr));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    private void result(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        logger.info("调用结果: {}", responseBody);
    }
}


