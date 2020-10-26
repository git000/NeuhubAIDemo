package neuhub;


import com.alibaba.fastjson.JSON;
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
 * {@link NeuhubAIDemoTester#faceCompare()} 人脸对比接口
 * {@link NeuhubAIDemoTester#facePropV1()} 人脸检测与属性分析接口
 * {@link NeuhubAIDemoTester#detectHacknessV1()} 人脸活体检测接口
 * {@link NeuhubAIDemoTester#food()} 菜品识别接口
 * {@link NeuhubAIDemoTester#humanDetect()} 人体检测接口
 * {@link NeuhubAIDemoTester#ocrIdCard()} 身份证识别接口
 * {@link NeuhubAIDemoTester#ocrInvoice()} 增值税发票识别接口
 * {@link NeuhubAIDemoTester#politiciansRecognition()} 特定人物识别接口
 * {@link NeuhubAIDemoTester#lexer()} 词法分析接口
 * {@link NeuhubAIDemoTester#poseEstimation()} 人体关键点检测接口
 * {@link NeuhubAIDemoTester#searchFace()} 人脸搜索接口
 * {@link NeuhubAIDemoTester#faceGroupCreate()} 创建人脸分组接口
 * {@link NeuhubAIDemoTester#faceGroupDelete()} 删除人脸分组接口
 * {@link NeuhubAIDemoTester#faceCreate()} 创建人脸接口
 * {@link NeuhubAIDemoTester#faceDelete()} 删除人脸接口
 * {@link NeuhubAIDemoTester#getFaceGroupList()} 获取人脸分组列表接口
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
 * {@link NeuhubAIDemoTester#garbageImageSearch()} 垃圾分类图像识别接口
 * {@link NeuhubAIDemoTester#garbageVoiceSearch()} 垃圾分类语音识别接口
 * {@link NeuhubAIDemoTester#garbageTextSearch()} 垃圾分类文本识别接口
 * {@link NeuhubAIDemoTester#humanParsing()} 细粒度人像分割接口
 * {@link NeuhubAIDemoTester#personreid()} 行人重识别接口
 * {@link NeuhubAIDemoTester#image_recognition_product()} 通用商品识别接口
 * {@link NeuhubAIDemoTester#vehicleDetection()} 车辆检测接口
 * {@link NeuhubAIDemoTester#imageSearchIndex()} 通用图片搜索图片入库
 * {@link NeuhubAIDemoTester#imageSearchTask()} 通用图片搜索任务状态查询
 * {@link NeuhubAIDemoTester#imageSearch()} 通用图片搜索
 * {@link NeuhubAIDemoTester#universalForPesticide()} 化肥农药袋子识别
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
        String value = String.format("imageBase64=%s", encodedText);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/detectHacknessV1";

        //TODO  增加JSON参数，此接口暂时无法调通
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisual",true);
        map.put("imgBase64Visual",encodedText);
        map.put("imgBase64Nir",encodedText);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class,map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void faceCompare() {
        byte[] face1Data = dataBinary(picture);
        byte[] face2Data = dataBinary(pictureCompare);
        String face1 = imageBase64(face1Data);
        String face2 = imageBase64(face2Data);
        String param = String.format("imageBase64_1=%s&imageBase64_2=%s", face1, face2);
        HttpEntity<String> requestEntity = new HttpEntity<>(param);
        String requestUrl = gatewayUrl + "/neuhub/face_compare";
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
        String value = String.format("imageBase64=%s", encodedText);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);

        //TODO 此接口增加请求体参数，暂时无法调通
        Map <String,Object> map = new HashMap<>();
        map.put("imgBase64",encodedText);
        String requestUrl = gatewayUrl + "/neuhub/facePropV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class,map);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void faceGroupCreate() {
        /**
         * groupName为用户创建分组的名称，根据分组名称完成创建后可以获得groupId，
         * 通过{@link NeuhubAIDemoTester#getFaceGroupList()} 查看分组信息
         */
        String groupName = "test0726";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/faceGroupCreate?groupName={groupName}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupName);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void faceCreate() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        String param = String.format("imageBase64=%s", encodedText);
        HttpEntity<Object> requestEntity = new HttpEntity<>(param);
        /**
         *  groupId需要调接口去创建
         *  {@link NeuhubAIDemoTester#faceGroupCreate()}
         */
        String groupId = "c0a0ed2b-a355-48c7-a4f7-c702fda26308";
        /**
         * 人脸图片的id值，用户自己生成，自己控制去重
         */
        String outerId = "0726testFace1";
        String requestUrl = gatewayUrl + "/neuhub/face_create?groupId={groupId}&outerId={outerId}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupId, outerId);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void faceDelete() {
        /**
         * groupId  需要调接口去创建 {@link NeuhubAIDemoTester#faceGroupCreate()}
         * outerId  待删除的人脸图片的id值
         */
        String groupId = "bfaca672-d954-4207-8e35-26aeeb276d71";
        String outerId = "0726testFace1";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/faceDelete?groupId=c0a0ed2b-a355-48c7-a4f7-c702fda26308&groupName=&outerId=0726testFace1";
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
    public void faceGroupDelete() {
        /**
         * groupId 为待删除的分组ID
         */
        String groupId = "5249f5d4-96ad-46b5-8e88-1e51be2d20c8";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/faceGroupDelete?groupId={groupId}";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupId);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void getFaceGroupList() {
        /**
         * start为查询起始位置，length为从查询起始位置开始查询的长度
         */
        int start = 0;
        int length = 5;
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, null);
        String requestUrl = gatewayUrl + "/neuhub/getFaceGroupList?start={start}&length={length}";
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("start", start);
        urlVariables.put("length", length);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, urlVariables);
        } catch (Exception e) {
            //调用API失败，错误处理
            throw new RuntimeException(e);
        }
        result(responseEntity);
    }

    @Test
    public void searchFace() {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        String param = String.format("imageBase64=%s", encodedText);
        HttpEntity<Object> requestEntity = new HttpEntity<>(param);
        //groupId为分组ID
        String groupId = "5249f5d4-96ad-46b5-8e88-1e51be2d20c8";
        String requestUrl = gatewayUrl + "/neuhub/faceSearchV1";
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, String.class, groupId);
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

    @Test
    public void food() throws Exception {
        byte[] data = dataBinary(picture);
        String encodedText = imageBase64(data);
        // Picture类为自创的实体类
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
        String requestId =  UUID.randomUUID().toString();
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


        //TODO 没有调通 没有返回音频编码
        //String requestId = "65845428-de85-11e8-9517-040973d59a1e";
        String requestId =  UUID.randomUUID().toString();
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
        HttpEntity<String> requestEntity = new HttpEntity<>("你好，京东！", httpHeaders);

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

    @Test
    public void garbageImageSearch() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        Map<String, Object> map = new HashMap<>();
        map.put("imgBase64", imageBase64);
        map.put("cityId", "310000");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);
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
    @Test
    public void personreid() {
        //需要传一张真实的身份证的照片
        byte[] data = dataBinary(picture);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data);
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

    @Test
    public void imageSearch() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/search";

        Map<String, Object> map = new HashMap<>();
        map.put("collection_name", comment);
        map.put("query_content", imageBase64);
        map.put("top_k", 50);
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

    @Test
    public void extract_img_colors() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/extract_img_colors";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        // params.add("image", imageBase64);
        imageBase64 = "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";
        params.add("image", imageBase64);
        params.add("color_count", "5");
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity(params, httpHeaders);
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
    public void detect_shelf_product() {

        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/detect_shelf_product";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        // headers.add("Content-Type", "application/json");
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Map<String, Object> map = new HashMap<>();
        // map.put("imgBase64", imageBase64);
        // String body = JSON.toJSONString(map);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("imgBase64", imageBase64);
        // HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        HttpEntity requestEntity = new HttpEntity<>(params, headers);
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
    public void recommend_outfits() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/recommend_outfits";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("image", imageBase64);
        HttpEntity requestEntity = new HttpEntity<>(params, headers);
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
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
    public void product_image_status() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_status";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("task_id", "92374205376234/food/1573113266.7619262");
        // List<Map<String, String>> mapList = new ArrayList<>();
        // Map<String, String> map1 = new HashMap<>();
        // map1.put("product_id", "204780573");
        // map1.put("image_content", imageBase64);
        // map1.put("image_id", "chuanda.jpg");
        // map1.put("image_info", "这是个卫衣");
        // mapList.add(map1);
        // paramMap.put("image_list", mapList);
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
    public void product_image_search() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_search";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("image_content", imageBase64);
        paramMap.put("collection_name", "服装商品库");
        paramMap.put("category", "服装");
        paramMap.put("topk", "10");
        // List<Map<String, String>> mapList = new ArrayList<>();
        // Map<String, String> map1 = new HashMap<>();
        // map1.put("product_id", "204780573");
        // map1.put("image_content", imageBase64);
        // map1.put("image_id", "chuanda.jpg");
        // map1.put("image_info", "这是个卫衣");
        // mapList.add(map1);
        // paramMap.put("image_list", mapList);
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
    public void product_image_info() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_info";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "服饰商品库");
        paramMap.put("product_id", "104631367");
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
    public void product_image_del_image() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_image";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "服饰商品库");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("product_id", "204780573");
        map1.put("image_id", "chuanda.jpg");
        mapList.add(map1);
        paramMap.put("image_id_list", mapList);
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
    public void product_image_del_product() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_product";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "服饰商品库");
        List<String> ids = new ArrayList<>();
        ids.add("104631367");
        paramMap.put("product_id_list", ids);
        // paramMap.put("collection_name", "服饰商品库");
        // List<Map<String, String>> mapList = new ArrayList<>();
        // Map<String, String> map1 = new HashMap<>();
        // map1.put("product_id", "204780573");
        // map1.put("image_id", "chuanda.jpg");
        // mapList.add(map1);
        // paramMap.put("image_id_list", mapList);
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
    public void product_image_del_collection() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String requestUrl = gatewayUrl + "/neuhub/product_image_del_collection";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("collection_name", "服饰商品库");
        String jsonString = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(jsonString, headers);
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
     * 接口不稳定
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
        // List<String> ids = new ArrayList<>();
        // ids.add("104631367");
        // paramMap.put("product_id_list", ids);
        // paramMap.put("collection_name", "服饰商品库");
        // List<Map<String, String>> mapList = new ArrayList<>();
        // Map<String, String> map1 = new HashMap<>();
        // map1.put("product_id", "204780573");
        // map1.put("image_id", "chuanda.jpg");
        // mapList.add(map1);
        // paramMap.put("image_id_list", mapList);
        String body = JSON.toJSONString(paramMap);
        HttpEntity requestEntity = new HttpEntity<>(body, headers);
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
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
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
        // params.add("self_flag",false);
        // params.add("show_count",3);
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
        String requestUrl = gatewayUrl + "/neuhub/phrase_extract?document=算法工程师的算法能力特别重要";
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

    @Test
    public void universalForPesticide() {
        byte[] data = dataBinary(picture);
        String imageBase64 = imageBase64(data);
        String value = String.format("imageBase64Str=%s", imageBase64);
        HttpEntity<String> requestEntity = new HttpEntity<>(value);
        String requestUrl = gatewayUrl + "/neuhub/universalForPesticide";
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


