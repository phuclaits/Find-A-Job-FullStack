import React from "react";
import FeatureJob from "./FeatureJob";
import { Link } from "react-router-dom";

const FeaturesJob = (props) => {
  return (
    <>
      <div class="row justify-content-center">
        <div class="col-xl-10">
          
          {/* <!-- single-job-content --> */}
          {props.dataFeature.content?.map((data) => {
            return (
              <Link to={`/detail-job/${data.id}`}>
                <FeatureJob key={data.id} data={data} /> 
              </Link>
            );
          })}
          {/* <!-- single-job-content --> */}
        </div>
      </div>
    </>
  );
};
export default FeaturesJob;
