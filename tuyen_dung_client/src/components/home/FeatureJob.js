import React from "react";
import moment from "moment";

const FeatureJob = (props) => {
  const handleSplitTime = (time) => {
    return "đăng " + moment(new Date(+time)).fromNow(true) + " trước";
  };

  const styles = {
    singleJobItems: {
      background: "#ffffff",
      borderRadius: "10px",
      boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
      padding: "20px",
      marginBottom: "20px",
      transition: "transform 0.3s ease",
    },
    singleJobItemsHover: {
      transform: "scale(1.02)",
    },
    jobItems: {
      display: "flex",
      alignItems: "center",
    },
    companyLogo: {
      width: "85px",
      height: "85px",
      borderRadius: "8px",
      objectFit: "cover",
    },
    jobTittle: {
      marginLeft: "20px",
    },
    jobTittleH4: {
      fontSize: "1.5rem",
      fontWeight: 600,
      color: "#333",
    },
    jobTittleUl: {
      display: "flex",
      flexWrap: "wrap",
      gap: "10px",
      listStyleType: "none",
      padding: 0,
      marginTop: "10px",
    },
    jobTittleLi: {
      fontSize: "0.9rem",
      color: "#666",
      display: "flex",
      alignItems: "center",
    },
    jobTittleIcon: {
      marginRight: "5px",
      color: "#4a90e2",
    },
    itemsLink2: {
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      marginTop: "20px",
    },
    itemsLinkButton: {
      backgroundColor: "#4a90e2",
      color: "#fff",
      padding: "8px 16px",
      borderRadius: "20px",
      textTransform: "uppercase",
      fontSize: "0.85rem",
      textDecoration: "none",
    },
    itemsLinkSpan: {
      display: "inline-block",
      padding: "5px 10px",
      backgroundColor: "#f0f0f0",
      color: "#666",
      marginLeft: "15px",
    },
  };

  return (
    <>
      <div
        className="single-job-items"
        style={styles.singleJobItems}
        onMouseEnter={(e) =>
          (e.currentTarget.style.transform = styles.singleJobItemsHover.transform)
        }
        onMouseLeave={(e) => (e.currentTarget.style.transform = "none")}
      >
        <div className="job-items" style={styles.jobItems}>
          <div className="company-img">
            <img
              src={props.data.companyThumbnailValue}
              alt="Company logo"
              style={styles.companyLogo}
            />
          </div>
          <div className="job-tittle" style={styles.jobTittle}>
            <h4 style={styles.jobTittleH4}>{props.data.nameValue}</h4>
            <ul style={styles.jobTittleUl}>
              <li style={styles.jobTittleLi}>
                <i className="fas fa-user-tie" style={styles.jobTittleIcon}></i>
                {props.data.categoryJobLevelCodeValue}
              </li>
              <li style={styles.jobTittleLi}>
                <i
                  className="fas fa-map-marker-alt"
                  style={styles.jobTittleIcon}
                ></i>
                {props.data.addressCodeValue}
              </li>
              <li style={styles.jobTittleLi}>
                <i
                  className="fas fa-money-bill-wave"
                  style={styles.jobTittleIcon}
                ></i>
                {props.data.salaryCodeValue}
              </li>
              <li style={styles.jobTittleLi}>
                <i
                  className="fas fa-briefcase"
                  style={styles.jobTittleIcon}
                ></i>
                {props.data.categoryWorktypeCodeValue}
              </li>
            </ul>
          </div>
        </div>
        <div className="items-link items-link2 f-right" style={styles.itemsLink2}>
          <span style={styles.itemsLinkSpan}>
            {handleSplitTime(props.data.timePostValue)}
          </span>
        </div>
      </div>
    </>
  );
};

export default FeatureJob;